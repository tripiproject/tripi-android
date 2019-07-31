package com.tripi.wallet.ui.fragment.backup_contracts_fragment;

import com.tripi.wallet.ui.base.base_fragment.BaseFragmentView;

import java.io.File;

public interface BackupContractsView extends BaseFragmentView {
    void setUpFile(String fileSize);

    void checkPermissionForCreateFile();

    void checkPermissionForBackupFile();

    void chooseShareMethod(String authority, File file);
}
