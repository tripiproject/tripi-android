package com.tripi.wallet.utils.migration_manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.tripi.wallet.datastorage.KeyStorage;
import com.tripi.wallet.datastorage.TripiSharedPreference;
import com.tripi.wallet.datastorage.TinyDB;
import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.model.contract.ContractCreationStatus;
import com.tripi.wallet.model.contract.Token;

import com.tripi.wallet.utils.DateCalculator;
import com.tripi.wallet.utils.crypto.KeyStoreHelper;

import java.io.File;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import java.util.List;

import io.realm.Realm;

import static com.tripi.wallet.utils.crypto.KeyStoreHelper.TRIPI_PIN_ALIAS;

public class MigrationManager {

    private final int migrateVersion_93 = 93;

    private final int migrateVersion_100 = 100;

    private final int migrateVersion_106 = 106;

    private final int migrateVersion_109 = 109;

    List<Integer> migrations = new ArrayList<>();

    public MigrationManager() {
        migrations.add(migrateVersion_93);
        migrations.add(migrateVersion_100);
        migrations.add(migrateVersion_106);
        migrations.add(migrateVersion_109);
    }

    public int makeMigration(int currentVersion, int migrationVersion, Context context, Realm realm) {
        int newMigrationVersion = migrationVersion;
        for (Integer version : migrations) {
            if (version > migrationVersion) {
                if (!migrate(version, context, realm)) {
                    return newMigrationVersion;
                }
                newMigrationVersion = version;
            }
        }
        return currentVersion;
    }

    private boolean migrate(int version, Context context, Realm realm) {
        switch (version) {
            case migrateVersion_93:
                renameSenderAddress(context);
                clearKeyFile(context);
                return true;
            case migrateVersion_100:
                resetContractCreationStatus(context);
                return true;
            case migrateVersion_106:
                resetContractAndTemplateTime(context);
                if(MigrationManager.migrateFromKeystore(context)
                        .equalsName(KeystoreMigrationResult.ERROR.name())) {
                    clearWallet(context, realm);
                }
                return true;
            case migrateVersion_109:
                renameCrowdsaleToToken(context);
                clearRealm(realm);
                return true;
            default:
                return false;
        }
    }

    private void clearKeyFile(Context context) {
        File file = new File(context.getFilesDir().getPath() + "/key_storage");
        if (file.exists()) {
            file.delete();
        }
    }

    private void renameSenderAddress(Context context) {
        TinyDB tinyDB = new TinyDB(context);
        List<Contract> contracts = tinyDB.getContractListWithoutToken();
        for (Contract contract : contracts) {
            if (contract.getSenderAddress().equals("Stub!")) {
                contract.setSenderAddress("");
            }
        }
        tinyDB.putContractListWithoutToken(contracts);

        List<Token> tokens = tinyDB.getTokenList();
        for (Token token : tokens) {
            if (token.getSenderAddress().equals("Stub!")) {
                token.setSenderAddress("");
            }
        }
        tinyDB.putTokenList(tokens);
    }

    private void resetContractCreationStatus(Context context) {
        TinyDB_94 tinyDB94 = new TinyDB_94(context);

        TinyDB_104 tinyDB = new TinyDB_104(context);
        List<Token104> tokens = new ArrayList<>();
        List<Contract104> contracts = new ArrayList<>();

        for (Contract94 contract94 : tinyDB94.getContractListWithoutToken()) {

            if (contract94.isHasBeenCreated() != null && contract94.isHasBeenCreated()) {
                contracts.add(new Contract104(contract94.getContractAddress(), contract94.getUiid(),
                        ContractCreationStatus.Created, contract94.getDate(), contract94.getSenderAddress(), contract94.getContractName()));
            } else {
                contracts.add(new Contract104(contract94.getContractAddress(), contract94.getUiid(),
                        ContractCreationStatus.Unconfirmed, contract94.getDate(), contract94.getSenderAddress(), contract94.getContractName()));
            }
        }
        tinyDB.putContractListWithoutToken(contracts);

        for (Token94 token94 : tinyDB94.getTokenList()) {
            if (token94.isHasBeenCreated() != null && token94.isHasBeenCreated()) {
                tokens.add(new Token104(token94.getContractAddress(), token94.getUiid(), ContractCreationStatus.Created,
                        token94.getDate(), token94.getSenderAddress(), token94.getContractName()));
            } else {
                tokens.add(new Token104(token94.getContractAddress(), token94.getUiid(), ContractCreationStatus.Unconfirmed,
                        token94.getDate(), token94.getSenderAddress(), token94.getContractName()));
            }
        }
        tinyDB.putTokenList(tokens);
    }

    private void resetContractAndTemplateTime(Context context) {
        TinyDB_104 tinyDB104 = new TinyDB_104(context);
        TinyDB tinyDB = new TinyDB(context);

        List<Contract> contracts = new ArrayList<>();
        List<Token> tokens = new ArrayList<>();
        List<ContractTemplate> contractTemplates = new ArrayList<>();

        for (Contract104 contract104 : tinyDB104.getContractListWithoutToken()) {
            contracts.add(new Contract(contract104.getContractAddress(), contract104.getUiid(), contract104.getContractName(), contract104.getCreationStatus(), convertDateToLong(contract104.getDate()), contract104.getSenderAddress(), contract104.isSubscribe()));
        }
        tinyDB.putContractListWithoutToken(contracts);

        for (Token104 token104 : tinyDB104.getTokenList()) {
            tokens.add(new Token(token104.getContractAddress(), token104.getUiid(), token104.getContractName(), token104.getCreationStatus(), convertDateToLong(token104.getDate()), token104.getSenderAddress(), token104.isSubscribe()));
        }
        tinyDB.putTokenList(tokens);

        for (ContractTemplate104 contractTemplate104 : tinyDB104.getContractTemplateList()) {
            contractTemplates.add(new ContractTemplate(contractTemplate104.getName(), convertDateToLong(contractTemplate104.getDate()), contractTemplate104.getContractType(), contractTemplate104.getUuid(), contractTemplate104.isFullContractTemplate(), contractTemplate104.isSelectedABI()));
        }
        tinyDB.putContractTemplate(contractTemplates);
    }

    private Long convertDateToLong(String date) {
        return DateCalculator.getLongDate(date);
    }


    public static KeystoreMigrationResult migrateFromKeystore(Context context) {
        switch (migratePassphrase(context)) {
            case ERROR:
                return KeystoreMigrationResult.ERROR;
            case SUCCESS:
                migratePinCode(context);
                return KeystoreMigrationResult.SUCCESS;
            case NOT_NEED:
                return KeystoreMigrationResult.NOT_NEED;
            default:
                return KeystoreMigrationResult.ERROR;
        }
    }

    private static KeystoreMigrationResult migratePassphrase(Context context) {
        if (TextUtils.isEmpty(TripiSharedPreference.getInstance().getKeystoreMigrationState(context))) {
            String encryptedSaltPassphrase = TripiSharedPreference.getInstance().getSeed(context);
            if (!TextUtils.isEmpty(encryptedSaltPassphrase)) {
                try {
                    KeyStoreHelper.createKeys(context, TRIPI_PIN_ALIAS);
                    byte[] bytes = KeyStoreHelper.decryptToBytes(TRIPI_PIN_ALIAS, encryptedSaltPassphrase);
                    savePassphraseSalt(context, bytes);
                    TripiSharedPreference.getInstance().setKeyStoreMigrationState(context, KeystoreMigrationResult.SUCCESS.name());
                    return KeystoreMigrationResult.SUCCESS;
                } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | RuntimeException e) {
                    return KeystoreMigrationResult.ERROR;
                }
            } else {
                TripiSharedPreference.getInstance().setKeyStoreMigrationState(context, KeystoreMigrationResult.NOT_NEED.name());
                return KeystoreMigrationResult.NOT_NEED;
            }
        } else {
            return KeystoreMigrationResult.NOT_NEED;
        }
    }

    private static void migratePinCode(Context context) {
        boolean isSixDigit;
        String encryptedPinHash = TripiSharedPreference.getInstance().getSixDigitPassword(context);
        isSixDigit = !TextUtils.isEmpty(encryptedPinHash);
        if (!isSixDigit) {
            encryptedPinHash = TripiSharedPreference.getInstance().getPassword(context);
            if (TextUtils.isEmpty(encryptedPinHash)) {
                return;
            }
        }
        String decrypt = KeyStoreHelper.decrypt(TRIPI_PIN_ALIAS, encryptedPinHash);
        if (isSixDigit) {
            TripiSharedPreference.getInstance().saveSixDigitPassword(context, decrypt);
        } else {
            TripiSharedPreference.getInstance().savePassword(context, decrypt);
        }
    }

    private static void savePassphraseSalt(Context context, byte[] saltPassphrase) {
        String base64 = Base64.encodeToString(saltPassphrase, Base64.DEFAULT);
        TripiSharedPreference.getInstance().saveSeed(context, base64);
    }

    private void clearWallet(Context context, Realm realm) {
        TripiSharedPreference.getInstance().forceClear(context);
        KeyStorage.getInstance().clearKeyStorage();
        realm.removeAllChangeListeners();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
        TinyDB db = new TinyDB(context);
        db.clearTokenList();
        db.clearContractList();
        db.clearTemplateList();
    }

    private void clearRealm(Realm realm){
        realm.removeAllChangeListeners();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    private void renameCrowdsaleToToken(Context context){
        TinyDB tinyDB = new TinyDB(context);
        List<ContractTemplate> contractTemplates = tinyDB.getContractTemplateList();
        for(ContractTemplate contractTemplate : contractTemplates){
            if(contractTemplate.getContractType().equals("crowdsale")){
                contractTemplate.setContractType("token");
            }
        }
        tinyDB.putContractTemplate(contractTemplates);
    }

}
