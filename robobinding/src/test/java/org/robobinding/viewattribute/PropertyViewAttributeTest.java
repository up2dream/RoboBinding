/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.property.ValueModelUtils;

import android.content.Context;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class PropertyViewAttributeTest
{
	private static final String PROPERTY_NAME = "property_name";
	private static final boolean ONE_WAY_BINDING = false;
	private static final boolean TWO_WAY_BINDING = true;
	private static final boolean PRE_INITIALIZE_VIEW = true;
	private static final boolean DONT_PRE_INITIALIZE_VIEW = false;
	private static final int A_NEW_VALUE = 5;
	
	private PresentationModelAdapter presentationModelAdapter;
	private Context context;
	private PropertyValueModel<Integer> valueModel = ValueModelUtils.createInteger(-1);
	private PropertyViewAttributeSpy propertyViewAttributeSpy;
	
	@Before
	public void setUp()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@Test
	public void givenABoundPropertyViewAttribute_WhenValueModelIsUpdated_ThenNewValueShouldBePassedToThePropertyViewAttribute()
	{
		initializeAndBindPropertyViewAttribute(ONE_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		
		valueModel.setValue(A_NEW_VALUE);
		
		assertThat(propertyViewAttributeSpy.updatedValue, is(A_NEW_VALUE));
	}
	
	@Test
	public void givenAPropertyViewAttributeWithTwoWayBinding_WhenTheViewIsUpdated_ThenValueModelShouldBeUpdated()
	{
		initializeAndBindPropertyViewAttribute(TWO_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		
		propertyViewAttributeSpy.simulateViewUpdate(A_NEW_VALUE);
	
		assertThat(valueModel.getValue(), is(A_NEW_VALUE));
	}
	
	@Test
	public void givenAPropertyViewAttributeWithTwoWayBinding_WhenTheViewIsUpdated_ThenViewShouldNotReceiveAFurtherUpdate()
	{
		initializeAndBindPropertyViewAttribute(TWO_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		
		propertyViewAttributeSpy.simulateViewUpdate(A_NEW_VALUE);
	
		assertThat(propertyViewAttributeSpy.viewUpdateNotificationCount, is(0));
	}
	
	@Test
	public void givenAPropertyViewAttributeWithTwoWayBinding_WhenValueModelIsUpdated_ThenViewShouldReceiveOnlyASingleUpdate()
	{
		initializeAndBindPropertyViewAttribute(TWO_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		
		valueModel.setValue(A_NEW_VALUE);
	
		assertThat(propertyViewAttributeSpy.viewUpdateNotificationCount, is(1));
	}
	
	@Test
	public void givenPreInitializeViewFlag_ThenPreInitializeTheViewToReflectTheValueModel()
	{
		initializeAndBindPropertyViewAttribute(ONE_WAY_BINDING, PRE_INITIALIZE_VIEW);
		
		assertTrue(propertyViewAttributeSpy.viewInitialized);
	}
	
	@Test
	public void givenNoPreInitializeViewFlag_ThenDontPreInitializeTheView()
	{
		initializeAndBindPropertyViewAttribute(ONE_WAY_BINDING, DONT_PRE_INITIALIZE_VIEW);
		
		assertFalse(propertyViewAttributeSpy.viewInitialized);
	}
	
	private void initializeAndBindPropertyViewAttribute(boolean twoWayBinding, boolean preInitializeView)
	{
		PropertyBindingDetails propertyBindingDetails = new PropertyBindingDetails(PROPERTY_NAME, twoWayBinding, preInitializeView);
		
		if (twoWayBinding)
			when(presentationModelAdapter.<Integer>getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		else
			when(presentationModelAdapter.<Integer>getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
		
		propertyViewAttributeSpy = new PropertyViewAttributeSpy(propertyBindingDetails);
		propertyViewAttributeSpy.bind(presentationModelAdapter, context);
	}
	
	private static class PropertyViewAttributeSpy extends AbstractPropertyViewAttribute<Integer>
	{
		int viewUpdateNotificationCount;
		int updatedValue;
		boolean viewInitialized;
		private PropertyValueModel<Integer> valueModelUpdatedByView;
		
		public PropertyViewAttributeSpy(PropertyBindingDetails propertyBindingDetails)
		{
			super(propertyBindingDetails);
		}
		
		public void simulateViewUpdate(int newValue)
		{
			valueModelUpdatedByView.setValue(newValue);
		}

		@Override
		protected void observeChangesOnTheView(PropertyValueModel<Integer> valueModel)
		{
			valueModelUpdatedByView = valueModel;
		}

		@Override
		protected void valueModelUpdated(Integer newValue)
		{
			this.updatedValue = newValue;
			viewUpdateNotificationCount++;
			viewInitialized = true;
		}
	}
}