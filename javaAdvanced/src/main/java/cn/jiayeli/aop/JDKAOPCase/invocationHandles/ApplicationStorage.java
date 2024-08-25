package cn.jiayeli.aop.JDKAOPCase.invocationHandles;

public  class ApplicationStorage {
    static String ApplicationId;

    public static void setApplicationId(String applicationId) {
        ApplicationId = applicationId;
    }

    public static String getApplicationId() {
        return ApplicationId;
    }
}
