package icbm.classic.content.blast.redmatter;

import icbm.classic.api.actions.status.IActionStatus;
import icbm.classic.config.blast.ConfigBlast;
import icbm.classic.lib.actions.status.ActionResponses;
import icbm.classic.content.blast.imp.BlastBase;

import javax.annotation.Nonnull;

/**
 * Blast that exists purely to spawn the redmatter entity into the world
 * <p>
 * Created by Dark(DarkGuardsman, Robin) on 4/19/2020.
 */
public class BlastRedmatterSpawner extends BlastBase
{
    @Nonnull
    @Override
    public IActionStatus triggerBlast()
    {
        //Build entity
        final EntityRedmatter entityRedmatter = new EntityRedmatter(world());
        entityRedmatter.setPosition(x(), y(), z());
        entityRedmatter.setBlastSize(getBlastRadius());
        entityRedmatter.setBlastMaxSize(ConfigBlast.redmatter.MAX_SIZE);

        //Attempt to spawn
        if (world().spawnEntity(entityRedmatter))
        {
            return ActionResponses.COMPLETED;
        }
        return ActionResponses.ENTITY_SPAWN_FAILED;
    }
}
