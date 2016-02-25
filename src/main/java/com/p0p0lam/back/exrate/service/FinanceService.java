package com.p0p0lam.back.exrate.service;

import com.p0p0lam.back.exrate.model.finance.Response;

/**
 * Created by Sergey on 22.02.2016.
 */
public interface FinanceService {
    int DEFAULT_SYNC_PERIOD_MINUTES = 15;
    boolean isRunning();
   void runUpdateIfNeed();
    void getData();
}
