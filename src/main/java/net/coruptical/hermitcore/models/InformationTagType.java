package net.coruptical.hermitcore.models;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;


// PersistentDataType for storing Information.java class.
public class InformationTagType implements PersistentDataType<byte[], Information> {


    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<Information> getComplexType() {
        return Information.class;
    }

    @Override
    public byte[] toPrimitive(Information complex, PersistentDataAdapterContext context) {
        return SerializationUtils.serialize(complex);
    }

    @Override
    public Information fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
        try{
            InputStream is = new ByteArrayInputStream(primitive);
            ObjectInputStream ois = new ObjectInputStream(is);
            return (Information) ois.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
