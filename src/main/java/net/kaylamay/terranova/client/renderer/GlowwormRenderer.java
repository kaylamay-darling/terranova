package net.kaylamay.terranova.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kaylamay.terranova.client.model.GlowwormModel;
import net.kaylamay.terranova.client.model.GlowwormRenderState;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.kaylamay.terranova.TerraNova;
import org.joml.Quaternionf;

public class GlowwormRenderer extends MobRenderer<GlowwormEntity, GlowwormRenderState, GlowwormModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(TerraNova.MODID, "textures/entity/glowworm.png");

    public GlowwormRenderer(EntityRendererProvider.Context context) {
        super(context, new GlowwormModel(context.bakeLayer(GlowwormModel.LAYER_LOCATION)), 0.2f);
    }

    @Override
    public Identifier getTextureLocation(GlowwormRenderState state) {
        return TEXTURE;
    }

    @Override
    public GlowwormRenderState createRenderState() {
        return new GlowwormRenderState();
    }

    @Override
    public void extractRenderState(GlowwormEntity entity, GlowwormRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.yRot = entity.getYRot();
        state.xRot = entity.getXRot();
    }

    @Override
    protected void scale(GlowwormRenderState state, PoseStack poseStack) {
        poseStack.scale(2.0F, 2.0F, 2.0F);
        super.scale(state, poseStack);
    }

    @Override
    protected void setupRotations(GlowwormRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
        Quaternionf cameraRotation = new Quaternionf(Minecraft.getInstance().gameRenderer.mainCamera().rotation());
        poseStack.mulPose(cameraRotation);
    }

    @Override
    protected RenderType getRenderType(GlowwormRenderState state, boolean isBodyVisible, boolean forceTransparent, boolean appearGlowing) {
        return RenderTypes.entityTranslucentEmissive(TEXTURE);
    }
}