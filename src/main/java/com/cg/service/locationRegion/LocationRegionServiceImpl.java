package com.cg.service.locationRegion;

import com.cg.model.LocationRegion;
import com.cg.repository.ILocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class LocationRegionServiceImpl implements ILocationRegionService {

    @Autowired
    private ILocationRegionRepository locationRegionRepository;


    @Override
    public List<LocationRegion> findAll() {
        return null;
    }

    @Override
    public LocationRegion findById(Long id) {
        return null;
    }

    @Override
    public void save(LocationRegion locationRegion) {
        locationRegionRepository.save(locationRegion);
    }

    @Override
    public void delete(Long id) {

    }


}
