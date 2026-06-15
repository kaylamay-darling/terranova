package net.kaylamay.terranova.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public class GlowwormModel extends EntityModel<GlowwormRenderState> {

    private final ModelPart body;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(TerraNova.MODID, "glowworm"),
            "main"
    );

    public GlowwormModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F),
                PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 2, 1);
    }

    @Override
    public void setupAnim(GlowwormRenderState state) {
        super.setupAnim(state);
    }
}
