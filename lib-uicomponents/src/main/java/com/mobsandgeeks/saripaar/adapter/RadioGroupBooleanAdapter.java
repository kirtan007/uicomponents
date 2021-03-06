/*
 * Copyright (C) 2015 Mobs & Geeks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mobsandgeeks.saripaar.adapter;

import android.view.View;
import android.widget.RadioGroup;

import com.mobsandgeeks.saripaar.exception.ConversionException;

import java.lang.annotation.Annotation;


/**
 * Adapter that returns a {@link Boolean} value from a {@link RadioGroup}.
 *
 * @author Ragunath Jawahar {@literal <rj@mobsandgeeks.com>}
 * @since 2.0
 */
public class RadioGroupBooleanAdapter implements ViewDataAdapter<RadioGroup, Boolean> {

    @Override
    public Boolean getData(RadioGroup radioGroup) throws ConversionException {
        return radioGroup.getCheckedRadioButtonId() != View.NO_ID;
    }

    @Override
    public <T extends Annotation> boolean containsOptionalValue(final RadioGroup radioGroup,
            final T ruleAnnotation) {
        return radioGroup.getCheckedRadioButtonId() == View.NO_ID;
    }
}
