package ld.maven.ldspringjpahib.model.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ld.maven.ldspringjpahib.model.subobjects.JsonEquipmentDetails;
import ld.maven.ldspringjpahib.model.subobjects.JsonGenericObject;
import ld.maven.ldspringjpahib.service.EquipmentService;

public class JsonPostgresType implements UserType {
	
	private static final Logger LOG = LoggerFactory.getLogger(JsonPostgresType.class);

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.JAVA_OBJECT};
	}

	@Override
	public Class returnedClass() {
		return JsonEquipmentDetails.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == null) {
			return y == null;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		LOG.info("nullSafeGet");
		final String cellContent = rs.getString(names[0]);
		LOG.info(cellContent);
		if (cellContent == null) {
			return null;
		}
		try {
			// On fait un mapping 
			final ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
			Object o = mapper.readValue(cellContent.getBytes("UTF-8"), returnedClass());
			// Manage miscFeature non modelized (free) Map
			JsonNode eqNode = mapper.readTree(cellContent.getBytes("UTF-8"));
			if (eqNode.hasNonNull("miscFeatures")) {
				// map miscFeatures in map provided by JsonGenericObject
				((JsonGenericObject)o).setMiscFeatures(mapper.readValue(eqNode.path("miscFeatures").toString(),Map.class));
			}
			
			
			return o;
		} catch (final Exception ex) {
			throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		LOG.info("nullSafeSet");
		if (value == null) {
			st.setNull(index, Types.OTHER);
			return;
		}
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			final StringWriter w = new StringWriter();
			mapper.writeValue(w, value);
			w.flush();
			LOG.info(w.toString());
			st.setObject(index, w.toString(), Types.OTHER);
		} catch (final Exception ex) {
            throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
        }

	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		LOG.info("deepCopy:"+value.getClass().getName());
		LOG.info(value.toString());
		try {
    		// use serialization to create a deep copy
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	ObjectOutputStream oos = new ObjectOutputStream(bos);
        	oos.writeObject(value);
        	oos.flush();
        	oos.close();
        	bos.close();
        	
        	ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
        	return new ObjectInputStream(bais).readObject();
        } catch (ClassNotFoundException | IOException ex) {
            throw new HibernateException(ex);
        }	
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) this.deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return this.deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return this.deepCopy(original);
	}

}
