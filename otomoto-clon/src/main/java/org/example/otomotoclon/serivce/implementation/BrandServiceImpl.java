package org.example.otomotoclon.serivce.implementation;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.dto.BrandDTO;
import org.example.otomotoclon.entity.Brand;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.exception.ObjectExistInDBException;
import org.example.otomotoclon.translator.BrandMapper;
import org.example.otomotoclon.repository.BrandRepository;
import org.example.otomotoclon.serivce.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public void create(BrandDTO brandDTO) throws ObjectExistInDBException {
        brandRepository.findBrandByName(brandDTO.getName()).ifPresent(value -> {
            throw new ObjectExistInDBException("Brand exists in database with given name");
        });
        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brandRepository.save(brand);
    }

    @Override
    public Brand getBrandByName(String name) throws ObjectDontExistInDBException {
        return brandRepository.findBrandByName(name)
                .orElseThrow(() -> new ObjectDontExistInDBException("Brand does not exist in the database"));
    }

    @Override
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return ResponseEntity.ok(
                brands.stream()
                    .map(brand -> brandMapper.toDto(brand))
                    .collect(Collectors.toList()));
    }
}
