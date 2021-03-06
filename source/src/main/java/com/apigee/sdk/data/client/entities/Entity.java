package com.apigee.sdk.data.client.entities;

import com.apigee.sdk.data.client.ApigeeDataClient;
import com.apigee.sdk.data.client.ApigeeDataClient.Query;
import com.apigee.sdk.data.client.response.ApiResponse;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.apigee.sdk.data.client.utils.JsonUtils.getUUIDProperty;
import static com.apigee.sdk.data.client.utils.JsonUtils.setBooleanProperty;
import static com.apigee.sdk.data.client.utils.JsonUtils.setFloatProperty;
import static com.apigee.sdk.data.client.utils.JsonUtils.setLongProperty;
import static com.apigee.sdk.data.client.utils.JsonUtils.setStringProperty;
import static com.apigee.sdk.data.client.utils.JsonUtils.setUUIDProperty;
import static com.apigee.sdk.data.client.utils.JsonUtils.toJsonString;

/**
 * Models an entity of any type as a local object. Type-specific 
 * classes are extended from this class.
 *
 * @see <a href="http://apigee.com/docs/app-services/content/app-services-data-model-1">API BaaS data model documentation</a>
 */
public class Entity {

    public final static String PROPERTY_UUID      = "uuid";
    public final static String PROPERTY_TYPE      = "type";
    public final static String PROPERTY_NAME      = "name";
    public final static String PROPERTY_METADATA  = "metadata";
    public final static String PROPERTY_CREATED   = "created";
    public final static String PROPERTY_MODIFIED  = "modified";
    public final static String PROPERTY_ACTIVATED = "activated";
    

    protected Map<String, JsonNode> properties = new HashMap<String, JsonNode>();
    private ApigeeDataClient dataClient;

    public static Map<String, Class<? extends Entity>> CLASS_FOR_ENTITY_TYPE = new HashMap<String, Class<? extends Entity>>();
    static {
        CLASS_FOR_ENTITY_TYPE.put(User.ENTITY_TYPE, User.class);
    }

    /**
     * Default constructor for instantiating an Entity object.
     */
    public Entity() {	
    }
    
    /**
     * Constructor for instantiating an Entity with a DataClient.
     * @param  dataClient  a DataClient object
     */
    public Entity(ApigeeDataClient dataClient) {
    	this.dataClient = dataClient;
    }

    /**
     * Constructor for instantiating an Entity with a DataClient
     * and entity type. Normally this is the constructor that should
     * be used to model an entity locally.
     * @param  dataClient  a DataClient object
     * @param  type  the 'type' property of the entity
     */
    public Entity(ApigeeDataClient dataClient, String type) {
    	this.dataClient = dataClient;
        setType(type);
    }
    
    /**
     * Gets the DataClient currently saved in the Entity object.
     * @return the DataClient instance
     */
    public ApigeeDataClient getDataClient() {
    	return dataClient;
    }

    /**
     * Sets the DataClient in the Entity object.
     * @param  dataClient  the DataClient instance
     */
    public void setDataClient(ApigeeDataClient dataClient) {
        this.dataClient = dataClient;
    }

    /**
     * Gets the 'type' of the Entity object.
     * @return the 'type' of the entity
     */
    @JsonIgnore
    public String getNativeType() {
        return getType();
    }

    /**
     * Adds the type and UUID properties to the Entity object, then 
     * returns all object properties.
     * @return a List object with the entity UUID and type
     */
    @JsonIgnore
    public List<String> getPropertyNames() {
        List<String> propertyNames = new ArrayList<String>();
        if( this.getProperties() != null ) {
            propertyNames.addAll(this.getProperties().keySet());
        }
        return propertyNames;
    }

    /**
     * Gets the String value of the specified Entity property.
     * @param  name  the name of the property
     * @return the property value. Returns null if the property has no value
     */
    public String getStringProperty(String name) {
        JsonNode val = this.properties.get(name);
        return val != null ? val.textValue() : null;
    }
    
    /**
     * Gets the boolean value of the specified Entity property.
     * @param  name  the name of the property
     * @return the property value
     */
    public boolean getBoolProperty(String name) {
    	return this.properties.get(name).booleanValue();
    }
    
    /**
     * Gets the Int value of the specified Entity property.
     * @param  name  the name of the property
     * @return the property value
     */
    public int getIntProperty(String name) {
    	return this.properties.get(name).intValue();
    }
    
    /**
     * Gets the Double value of the specified Entity property.
     * @param  name  the name of the property
     * @return the property value
     */
    public double getDoubleProperty(String name) {
    	return this.properties.get(name).doubleValue();
    }
    
    /**
     * Gets the long value of the specified Entity property.
     * @param  name  the name of the property
     * @return the property value
     */
    public long getLongProperty(String name) {
    	return this.properties.get(name).longValue();
    }

    /**
     * Gets the 'type' property of the Entity object.     
     * @return the Entity type
     */
    public String getType() {
        return getStringProperty(PROPERTY_TYPE);
    }

    /**
     * Sets the 'type' property of the Entity object.          
     * @param  type  the entity type
     */
    public void setType(String type) {
        setStringProperty(properties, PROPERTY_TYPE, type);
    }

    /**
     * Gets the 'uuid' property of the Entity object.     
     * @return the Entity UUID
     */
    public UUID getUuid() {
        return getUUIDProperty(properties, PROPERTY_UUID);
    }

    /**
     * Sets the 'uuid' property of the Entity object.     
     * @param  uuid  the entity UUID
     */
    public void setUuid(UUID uuid) {
        setUUIDProperty(properties, PROPERTY_UUID, uuid);
    }

    /**
     * Returns a HashMap of the Entity properties without keys.
     *
     * @return a HashMap object with no keys and the value of the Entity properties
     */
    @JsonAnyGetter
    public Map<String, JsonNode> getProperties() {
        return properties;
    }

    /**
     * Adds a property to the Entity object.
     *
     * @param  name  the name of the property to be set
     * @param  value the value of the property as a JsonNode object.
     *      If the value is null, the property will be removed from the object.
     * @see  <a href="http://jackson.codehaus.org/1.0.1/javadoc/org/codehaus/jackson/JsonNode.html">JsonNode</a> 
     */
    @JsonAnySetter
    public void setProperty(String name, JsonNode value) {
        if (value == null) {
            properties.remove(name);
        } else {
            properties.put(name, value);
        }
    }
    
    /**
     * Removes all properties from the Entity object, then adds multiple properties.
     *
     * @param  newProperties  a Map object that contains the 
     *      property names as keys and their values as values.
     *      Property values must be JsonNode objects. If the value 
     *      is null, the property will be removed from the object.
     * @see  <a href="http://jackson.codehaus.org/1.0.1/javadoc/org/codehaus/jackson/JsonNode.html">JsonNode</a> 
     */
    public void setProperties(Map<String,JsonNode> newProperties) {
    	properties.clear();
    	for (Map.Entry<String, JsonNode> entry : newProperties.entrySet()) {
    		setProperty(entry.getKey(), entry.getValue());
    	}
    }
  
    /**
     * Adds a property to the Entity object with a String value.
     * 
     * @param  name  the name of the property to be set
     * @param  value  the String value of the property
     */
    public void setProperty(String name, String value) {
        setStringProperty(properties, name, value);
    }

    /**
     * Adds a property to the Entity object with a boolean value.
     * 
     * @param  name  the name of the property to be set
     * @param  value  the boolean value of the property
     */
    public void setProperty(String name, boolean value) {
        setBooleanProperty(properties, name, value);
    }

    /**
     * Adds a property to the Entity object with a long value.
     * 
     * @param  name  the name of the property to be set
     * @param  value  the long value of the property
     */
    public void setProperty(String name, long value) {
        setLongProperty(properties, name, value);
    }

    /**
     * Adds a property to the Entity object with a int value.
     * 
     * @param  name  the name of the property to be set
     * @param  value  the int value of the property
     */
    public void setProperty(String name, int value) {
        setProperty(name, (long) value);
    }

    /**
     * Adds a property to the Entity object with a float value.
     * 
     * @param  name  the name of the property to be set
     * @param  value  the float value of the property
     */
    public void setProperty(String name, float value) {
        setFloatProperty(properties, name, value);
    }

    /**
     * Returns the Entity object as a JSON-formatted string
     */
    @Override
    public String toString() {
        return toJsonString(this);
    }

    /**
     * @y.exclude
     */
    public <T extends Entity> T toType(Class<T> t) {
        return toType(this, t);
    }

    /**
     * @y.exclude
     */
    public static <T extends Entity> T toType(Entity entity, Class<T> t) {
        if (entity == null) {
            return null;
        }
        T newEntity = null;
        if (entity.getClass().isAssignableFrom(t)) {
            try {
                newEntity = (t.newInstance());
                if ((newEntity.getNativeType() != null)
                        && newEntity.getNativeType().equals(entity.getType())) {
                    newEntity.properties = entity.properties;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newEntity;
    }

    /**
     * @y.exclude
     */
    public static <T extends Entity> List<T> toType(List<Entity> entities,
            Class<T> t) {
        List<T> l = new ArrayList<T>(entities != null ? entities.size() : 0);
        if (entities != null) {
            for (Entity entity : entities) {
                T newEntity = entity.toType(t);
                if (newEntity != null) {
                    l.add(newEntity);
                }
            }
        }
        return l;
    }
    
    /**
     * Fetches the current state of the entity from the server and saves
     * it in the Entity object. Runs synchronously.
     *      
     * @return an ApiResponse object
     */
    public ApiResponse fetch() {
    	ApiResponse response = new ApiResponse();
        String type = this.getType();
        UUID uuid = this.getUuid(); // may be NULL
        String entityId = null;
        if ( uuid != null ) {        	
        	entityId = uuid.toString();
        } else {
        	if (User.isSameType(type)) {
                String username = this.getStringProperty(User.PROPERTY_USERNAME);
                if ((username != null) && (username.length() > 0)) {            	    
            	    entityId = username;
                } else {
                    String error = "no_username_specified";
                    this.dataClient.writeLog(error);
                    response.setError(error);
                    //response.setErrorCode(error);
                    return response;
                }
            } else {
                String name = this.getStringProperty(PROPERTY_NAME);
                if ((name != null) && (name.length() > 0)) {                    
                    entityId = name;
                } else {
                    String error = "no_name_specified";
                    this.dataClient.writeLog(error);
                    response.setError(error);
                    //response.setErrorCode(error);
                    return response;
                }
            }
        }
        
        Query q = this.dataClient.queryEntitiesRequest("GET", null, null,
                this.dataClient.getOrganizationId(),  this.dataClient.getApplicationId(), type, entityId);
        response = q.getResponse();
        if (response.getError() != null) {
            this.dataClient.writeLog("Could not get entity.");
        } else {
            if ( response.getUser() != null ) {
        	    this.addProperties(response.getUser().getProperties());
            } else if ( response.getEntityCount() > 0 ) {
        	    Entity entity = response.getFirstEntity();
        	    this.setProperties(entity.getProperties());
            }
        }
        
        return response;
    }
    
    /**
     * Saves the Entity object as an entity on the server. Any
     * conflicting properties on the server will be overwritten. Runs synchronously.
     *      
     * @return  an ApiResponse object
     */
    public ApiResponse save() {
    	ApiResponse response = null;
        UUID uuid = this.getUuid();
        boolean entityAlreadyExists = false;
        
        if (ApigeeDataClient.isUuidValid(uuid)) {
            entityAlreadyExists = true;
        }
        
        // copy over all properties except some specific ones
        Map<String,Object> data = new HashMap<String,Object>();
        Set<String> keySet = this.properties.keySet();
        Iterator<String> keySetIter = keySet.iterator();
        
        while(keySetIter.hasNext()) {
        	String key = keySetIter.next();
        	if (!key.equals(PROPERTY_METADATA) &&
        		!key.equals(PROPERTY_CREATED) &&
        		!key.equals(PROPERTY_MODIFIED) &&
        		!key.equals(PROPERTY_ACTIVATED) &&
        		!key.equals(PROPERTY_UUID)) {
        		data.put(key, this.properties.get(key));
        	}
        }
        
        if (entityAlreadyExists) {
        	// update it
        	response = this.dataClient.updateEntity(uuid.toString(), data);
        } else {
        	// create it
        	response = this.dataClient.createEntity(data);
        }

        if ( response.getError() != null ) {
            this.dataClient.writeLog("Could not save entity.");
        } else {
        	if (response.getEntityCount() > 0) {
        		Entity entity = response.getFirstEntity();
        		this.setProperties(entity.getProperties());
        	}
        }
        
        return response;    	
    }
    
    /**
     * Deletes the entity on the server.
     *     
     * @return  an ApiResponse object
     */
    public ApiResponse destroy() {
    	ApiResponse response = new ApiResponse();
        String type = getType();
        String uuidAsString = null;
        UUID uuid = getUuid();
        if ( uuid != null ) {
        	uuidAsString = uuid.toString();
        } else {
        	String error = "Error trying to delete object: No UUID specified.";
        	this.dataClient.writeLog(error);
        	response.setError(error);
        	//response.setErrorCode(error);
        	return response;
        }
        
        response = this.dataClient.removeEntity(type, uuidAsString);
        
        if( (response != null) && (response.getError() != null) ) {
        	this.dataClient.writeLog("Entity could not be deleted.");
        } else {
        	this.properties.clear();
        }
        
        return response;
    }
    
    /**
     * Adds multiple properties to the Entity object. Pre-existing properties will
     * be preserved, unless there is a conflict, then the pre-existing property
     * will be overwritten.
     *
     * @param  properties  a Map object that contains the 
     *      property names as keys and their values as values.
     *      Property values must be JsonNode objects. If the value 
     *      is null, the property will be removed from the object.
     * @see  <a href="http://jackson.codehaus.org/1.0.1/javadoc/org/codehaus/jackson/JsonNode.html">JsonNode</a> 
     */
    public void addProperties(Map<String, JsonNode> properties) {
    	for (Map.Entry<String, JsonNode> entry : properties.entrySet()) {
    		setProperty(entry.getKey(), entry.getValue());
    	}
    }
    
    /**
     * Creates a connection between two entities.
     *
     * @param  connectType  the type of connection
     * @param  targetEntity  the UUID of the entity to connect to
     * @return an ApiResponse object
     */
    public ApiResponse connect(String connectType, Entity targetEntity) {
    	return this.dataClient.connectEntities(this.getType(),
				this.getUuid().toString(),
				connectType,
				targetEntity.getUuid().toString());
    }
    
    /**
     * Destroys a connection between two entities.
     *
     * @param  connectType  the type of connection
     * @param  targetEntity  the UUID of the entity to disconnect from
     * @return  an ApiResponse object
     */
    public ApiResponse disconnect(String connectType, Entity targetEntity) {
    	return this.dataClient.disconnectEntities(this.getType(),
    												this.getUuid().toString(),
    												connectType,
    												targetEntity.getUuid().toString());
    }

}
