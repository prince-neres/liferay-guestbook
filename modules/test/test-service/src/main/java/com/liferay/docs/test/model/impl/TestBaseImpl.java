/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.docs.test.model.impl;

import com.liferay.docs.test.model.Test;
import com.liferay.docs.test.service.TestLocalServiceUtil;

/**
 * The extended model base implementation for the Test service. Represents a row in the &quot;TEST_Test&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link TestImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TestImpl
 * @see Test
 * @generated
 */
public abstract class TestBaseImpl extends TestModelImpl implements Test {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a test model instance should use the <code>Test</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			TestLocalServiceUtil.addTest(this);
		}
		else {
			TestLocalServiceUtil.updateTest(this);
		}
	}

}