/*
 * Copyright 2005-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.common.location.impl;

import org.ameba.annotation.Measured;
import org.ameba.annotation.TxService;
import org.openwms.common.location.LocationType;
import org.openwms.common.location.LocationTypeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A LocationTypeServiceImpl is a transactional Spring managed bean.
 *
 * @author Heiko Scherrer
 */
@TxService
class LocationTypeServiceImpl implements LocationTypeService {

    private final LocationTypeRepository repository;

    LocationTypeServiceImpl(LocationTypeRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Measured
    @Transactional(readOnly = true)
    public List<LocationType> findAll() {
        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * The implementation uses the PK to find the LocationType to be removed and removes it.
     */
    @Override
    @Measured
    public void delete(List<LocationType> locationTypes) {
        locationTypes.forEach(
                locationType -> repository
                        .findByType(locationType.getType())
                        .ifPresent(
                                t -> repository.deleteById(t.getPk())
                        )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Measured
    public LocationType save(LocationType locationType) {
        return repository.save(locationType);
    }
}
