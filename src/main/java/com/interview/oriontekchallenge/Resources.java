package com.interview.oriontekchallenge;

public enum Resources {
    DEFAULT("cliente.fxml", "cliente.Cliente"),
    CLIENTE("cliente.fxml", "cliente.Cliente"),
    CLIENTE_DIRECCIONES("cliente_direcciones.fxml", "cliente.direcciones.ClienteDirecciones"),
    DIRECCION("direccion.fxml", "direccion.Direccion"),
    MAIN("main.fxml", "main.Main");

    private final String fxmlPath_;
    private final String bundlePath_;

    Resources(String fxmlPath, String bundlePath) {
        fxmlPath_ = fxmlPath;
        bundlePath_ = bundlePath;
    }

    public String getBundlePath() {
        return bundlePath_;
    }

    public String getFxmlPath() {
        return fxmlPath_;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "fxmlPath_='" + fxmlPath_ + '\'' +
                ", bundlePath_='" + bundlePath_ + '\'' +
                '}';
    }
}
