package icbm.classic.client.render.entity;

import com.builtbroken.mc.client.SharedAssets;
import com.builtbroken.mc.core.References;
import com.builtbroken.mc.lib.render.RenderUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import icbm.classic.ICBMClassic;
import icbm.classic.content.entity.EntityExplosion;
import icbm.classic.content.explosive.blast.BlastRedmatter;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderExplosion extends Render
{
    public static final ResourceLocation TEXTURE_FILE = new ResourceLocation(ICBMClassic.DOMAIN, References.TEXTURE_DIRECTORY + "blackhole.png");

    public Random random = new Random();

    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float par9)
    {
        EntityExplosion entityExplosion = (EntityExplosion) entity;

        if (entityExplosion.getBlast() != null)
        {
            // RedM atter Render
            if (entityExplosion.getBlast() instanceof BlastRedmatter)
            {
                final BlastRedmatter redmatter = (BlastRedmatter) entityExplosion.getBlast();
                final float scale = redmatter.getScaleFactor();

                Tessellator tessellator = Tessellator.instance;

                //=======================================================
                //Draw Sphere
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x, (float) y, (float) z);

                RenderUtility.enableBlending();
                RenderUtility.disableLighting();

                GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.9f);

                bindTexture(SharedAssets.GREY_TEXTURE);
                Sphere sphere = new Sphere();

                float radius = Math.max(BlastRedmatter.ENTITY_DESTROY_RADIUS * scale, 0.1f);
                sphere.draw(radius, 32, 32);

                // Enable Lighting/Glow Off
                RenderUtility.enableLighting();

                // Disable Blending
                RenderUtility.disableBlending();
                GL11.glPopMatrix();


                //=======================================================
                //Draw Vortex
                GL11.glPushMatrix();
                GL11.glDepthMask(false);

                RenderUtility.enableBlending();
                RenderUtility.disableLighting();

                GL11.glTranslated(x, y, z);
                GL11.glRotatef(-entity.ticksExisted, 0, 1, 0);

                float size = BlastRedmatter.ENTITY_DESTROY_RADIUS * scale * 2;

                this.bindTexture(TEXTURE_FILE);

                //top render
                tessellator.startDrawingQuads();
                tessellator.setBrightness(240);
                tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1F);
                tessellator.addVertexWithUV(-size, 0, -size, 0, 0);
                tessellator.addVertexWithUV(-size, 0, +size, 0, 1);
                tessellator.addVertexWithUV(+size, 0, +size, 1, 1);
                tessellator.addVertexWithUV(+size, 0, -size, 1, 0);
                tessellator.draw();

                //bottom render
                GL11.glRotatef(180, 1, 0, 0);
                tessellator.startDrawingQuads();
                tessellator.setBrightness(240);
                tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1F);
                tessellator.addVertexWithUV(-size, 0, -size, 1, 1);
                tessellator.addVertexWithUV(-size, 0, +size, 1, 0);
                tessellator.addVertexWithUV(+size, 0, +size, 0, 0);
                tessellator.addVertexWithUV(+size, 0, -size, 0, 1);
                tessellator.draw();

                // Enable Lighting/Glow Off
                RenderUtility.enableLighting();

                // Disable Blending
                RenderUtility.disableBlending();

                GL11.glDepthMask(true);
                GL11.glPopMatrix();


                //=======================================================

                /** Enderdragon Light */
                float ticks = entity.ticksExisted;

                while (ticks > 200)
                {
                    ticks -= 100;
                }

                RenderHelper.disableStandardItemLighting();
                float var41 = (5 + ticks) / 200.0F;
                float var51 = 0.0F;

                if (var41 > 0.8F)
                {
                    var51 = (var41 - 0.8F) / 0.2F;
                }


                Random rand = new Random(432L);

                GL11.glPushMatrix();
                GL11.glTranslatef((float) x, (float) y, (float) z);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glShadeModel(GL11.GL_SMOOTH);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDepthMask(false);


                GL11.glPushMatrix();
                GL11.glTranslatef(0.0F, 0, 0);
                int beamCount = (int) ((var41 + var41 * var41) / 2.0F * 60.0F);
                for (int i1 = 0; i1 < beamCount; ++i1)
                {
                    float beamLength = (rand.nextFloat() * 20.0F + 5.0F + var51 * 10.0F) * scale;
                    float beamWidth = (rand.nextFloat() * 2.0F + 1.0F + var51 * 2.0F) * scale;

                    //Random rotations TODO see if we need to rotate so much
                    GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(rand.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(rand.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(rand.nextFloat() * 360.0F + var41 * 90.0F, 0.0F, 0.0F, 1.0F);

                    //Draw spike shape
                    tessellator.startDrawing(6);

                    //center
                    tessellator.setColorRGBA_I(16777215, (int) (255.0F * (1.0F - var51)));
                    tessellator.addVertex(0.0D, 0.0D, 0.0D);

                    //Outside
                    tessellator.setColorRGBA_I(0, 0);
                    tessellator.addVertex(-0.866D * beamWidth, beamLength, -0.5F * beamWidth);
                    tessellator.addVertex(0.866D * beamWidth, beamLength, -0.5F * beamWidth);
                    tessellator.addVertex(0.0D, beamLength, 1.0F * beamWidth);
                    tessellator.addVertex(-0.866D * beamWidth, beamLength, -0.5F * beamWidth);

                    tessellator.draw();
                }
                GL11.glPopMatrix();


                GL11.glDepthMask(true);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glShadeModel(GL11.GL_FLAT);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
            else
            {
                if (entityExplosion.getBlast().getRenderModel() != null && entityExplosion.getBlast().getRenderResource() != null)
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) x, (float) y + 1F, (float) z);
                    GL11.glRotatef(entityExplosion.rotationPitch, 0.0F, 0.0F, 1.0F);
                    this.bindTexture(entityExplosion.getBlast().getRenderResource());
                    entityExplosion.getBlast().getRenderModel().render(entityExplosion, (float) x, (float) y, (float) z, par8, par9, 0.0625F);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    public void drawCircle(double x, double y, double radius, double accuracy)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        double da = Math.min((2.0 * Math.asin(1.0 / radius) / accuracy), 10000);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2d(x, y);

        for (double a = 0.0; a <= 2 * Math.PI; a += da)
        {
            GL11.glVertex2d(x + Math.cos(a) * radius, y + Math.sin(a) * radius);
        }

        GL11.glVertex2d(x + radius, y);
        GL11.glEnd();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return null;
    }

}
