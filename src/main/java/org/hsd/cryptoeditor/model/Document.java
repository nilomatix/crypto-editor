package org.hsd.cryptoeditor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.hsd.cryptoeditor.crypto.encryption.Encryption;
import org.hsd.cryptoeditor.crypto.encryption.NoEncryption;

import java.io.File;
import java.io.Serializable;

/**
 * Created by nils on 5/1/16.
 */
public class Document implements Serializable {

    private File file;

    private Encryption encryption;

    private StringProperty content = new SimpleStringProperty();

    public Document() {
        encryption = new NoEncryption();
    }

    public String getText() {
        return content.get();
    }

    public void setText(String s) {
        this.content.set(s);
    }

    public StringProperty textProperty() {
        return content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Encryption getEncryption() {
        return encryption;
    }

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
    }
}
