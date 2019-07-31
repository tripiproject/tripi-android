package com.tripi.wallet.ui.fragment.restore_contracts_fragment;

import com.tripi.wallet.model.ContractTemplate;
import com.tripi.wallet.model.backup.Backup;
import com.tripi.wallet.model.backup.ContractJSON;
import com.tripi.wallet.model.backup.TemplateJSON;
import com.tripi.wallet.model.contract.Contract;
import com.tripi.wallet.model.contract.Token;

import java.io.File;
import java.util.List;

public interface RestoreContractsInteractor {
    Backup getBackupFromFile(File restoreFile) throws Exception;

    List<ContractTemplate> getContractTemplates();

    ContractTemplate importTemplate(TemplateJSON templateJSON, List<ContractTemplate> templates);

    void putListWithoutToken(List<Contract> contractList);

    void putTokenList(List<Token> tokenList);

    boolean validateContractCreationAddress(ContractJSON contractJSON, List<TemplateJSON> templates);

    boolean getTemplateValidity(TemplateJSON templateJSON);

    boolean getContractValidity(ContractJSON contractJSON);
}
