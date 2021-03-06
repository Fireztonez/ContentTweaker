package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.contenttweaker.modules.materials.CTMaterialSystem;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CTMaterial implements IMaterial {
    private Material material;

    public CTMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String getName() {
        return this.material.getName();
    }

    @Override
    public int getColor() {
        return this.material.getColor().getRGB();
    }

    @Override
    public boolean isHasEffect() {
        return this.material.isHasEffect();
    }

    @Override
    public String getUnlocalizedName() {
        return this.material.getUnlocalizedName();
    }

    @Override
    public List<IMaterialPart> registerParts(String[] partNames) {
        return CTMaterialSystem.registerPartsForMaterial(this.material, partNames);
    }

    @Override
    public List<IMaterialPart> registerParts(IPart[] parts) {
        List<String> names = Arrays.stream(parts).map(IPart::getName).collect(Collectors.toList());
        return this.registerParts(names.toArray(new String[names.size()]));
    }

    @Override
    public IMaterialPart registerPart(String partName) {
        return this.registerParts(new String[]{partName}).get(0);
    }

    @Override
    public IMaterialPart registerPart(IPart part) {
        return this.registerParts(new IPart[]{part}).get(0);
    }
}
