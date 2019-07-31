package com.tripi.wallet.dataprovider.services.update_service.listeners;

import com.tripi.wallet.model.gson.qstore.ContractPurchase;

public interface ContractPurchaseListener {
    void onContractPurchased(ContractPurchase purchaseData);
}
