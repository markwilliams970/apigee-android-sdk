package com.apigee.sdk.apm.android;

import java.beans.PropertyChangeSupport;

import com.apigee.sdk.apm.android.ApplicationConfigurationService;
import com.apigee.sdk.apm.android.LoadConfigurationException;
import com.apigee.sdk.apm.android.model.ApplicationConfigurationModel;
import com.apigee.sdk.apm.android.model.App;

public abstract class BaseWebManagerClientConfigLoader implements ApplicationConfigurationService {

	PropertyChangeSupport configChangeSupport;
	protected ApplicationConfigurationModel configurationModel;

	protected App compositeApplicationConfigurationModel;

	public BaseWebManagerClientConfigLoader() {
		configurationModel = new ApplicationConfigurationModel();
		// configChangeSupport = new PropertyChangeSupport(this);
	}

	
	public ApplicationConfigurationModel getConfigurations() {
		return configurationModel;
	}

	
	public abstract void loadConfigurations(String applicationId)
			throws LoadConfigurationException;

	@Override
	public App getCompositeApplicationConfigurationModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
