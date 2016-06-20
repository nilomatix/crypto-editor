package org.hsd.cryptoeditor.concurrency;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.commons.io.IOUtils;
import org.hsd.cryptoeditor.crypto.CryptoService;
import org.hsd.cryptoeditor.crypto.encryption.Encryption;
import org.hsd.cryptoeditor.crypto.encryption.EncryptionType;
import org.hsd.cryptoeditor.crypto.grapher.Cryptographer;
import org.hsd.cryptoeditor.dialog.DialogService;
import org.hsd.cryptoeditor.doc.PersistenceDTO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

/**
 * Created by nils on 6/20/16.
 */
public class EncryptionService extends Service<byte[]> {

    private InputStream contentInput;

    private Encryption encryption;

    private char[] password;

    public void setEncryption(Encryption encryption) {
        this.encryption = encryption;
        if (encryption.getType().isPBEType()) {
            Optional<String> passwordOptional = DialogService.getInstance().showPasswordDialog();
            if (passwordOptional.isPresent()) {
                this.password = passwordOptional.get().toCharArray();
            }
        }
    }

    public void setContentInput(InputStream contentInput) {
        this.contentInput = contentInput;
    }

    @Override
    protected Task<byte[]> createTask() {
        return new Task<byte[]>() {
            protected byte[] call() throws Exception {
                InputStream source = contentInput;
                if (encryption.getType() != EncryptionType.NONE) {
                    Cryptographer c = CryptoService.getInstance().getCryptographer(encryption, password);
                    source = c.getEncryptor(contentInput);
                }
                return IOUtils.toByteArray(source);
            }
        };
    }
}
