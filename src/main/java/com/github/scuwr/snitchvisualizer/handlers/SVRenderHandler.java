package com.github.scuwr.snitchvisualizer.handlers;

import org.lwjgl.opengl.GL11;

import com.github.scuwr.snitchvisualizer.SV;
import com.github.scuwr.snitchvisualizer.classobjects.Snitch;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class SVRenderHandler {
	public static boolean enabled = true;
	
	@SubscribeEvent
	public void eventRenderWorld(RenderWorldLastEvent event){
		if(SVFileIOHandler.isDone && SV.instance.config.rendering){
			try{
				for(Snitch n : SV.instance.snitchList){
					if(n.getDistance() < 96){
						GL11.glBlendFunc(770, 771);
				        GL11.glLineWidth(5.0F);
				        GL11.glDisable(2896);
				        GL11.glDisable(3553);
				        GL11.glEnable(3042);
				        GL11.glEnable(2848);
				        GL11.glEnable(32925);
				        GL11.glAlphaFunc(516, 0.09F);
				        GL11.glDepthMask(false);
				          
				        GL11.glPushMatrix();
				        double px = -(RenderManager.renderPosX - n.x);
				        double py = -(RenderManager.renderPosY - n.y);
				        double pz = -(RenderManager.renderPosZ - n.z);
				        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(px - 10.99, py - 11.99, pz - 10.99, px + 11.99, py + 10.99, pz + 11.99);
					
				        int color = getColor(n.hoursToDate());
				        switch(color){
				        	case 0: GL11.glColor4d(0, 0.56, 1, 0.25); break;
				        	case 1: GL11.glColor4d(0.11, 0.91, 0.0D, 0.25); break;
				        	case 2: GL11.glColor4d(0.88, 0.83, 0.0D, 0.25); break;
				        	case 3: GL11.glColor4d(0.75, 0.0, 0.91, 0.25); break;
				        }
				        drawCrossedOutlinedBoundingBox(bb);
				        switch(color){
			        		case 0: GL11.glColor4d(0, 0.56, 1, 0.1); break;
			        		case 1: GL11.glColor4d(0.11, 0.91, 0.0D, 0.1); break;
			        		case 2: GL11.glColor4d(0.88, 0.83, 0.0D, 0.1); break;
			        		case 3: GL11.glColor4d(0.75, 0.0, 0.91, 0.1); break;
				        }
				        drawBoundingBoxQuads(bb);
				        GL11.glPopMatrix();
				        
				        GL11.glDepthMask(true);
				        GL11.glDisable(2848);
				        GL11.glDisable(3042);
				        GL11.glEnable(3553);
				        GL11.glEnable(2929);
				        GL11.glDisable(32925);
				        GL11.glEnable(2896);  
	
				        GL11.glBlendFunc(770, 771);
				        GL11.glLineWidth(5.0F);
				        GL11.glDisable(2896);
				        GL11.glDisable(2929);
				        GL11.glDisable(3553);
				        GL11.glEnable(3042);
				        GL11.glEnable(2848);
				        GL11.glEnable(32925);
				        GL11.glAlphaFunc(516, 0.09F);
				        GL11.glDepthMask(false);
				          
				        GL11.glPushMatrix();
				        px = -(RenderManager.renderPosX - n.x);
				        py = -(RenderManager.renderPosY - n.y);
				        pz = -(RenderManager.renderPosZ - n.z);
				        bb = AxisAlignedBB.getBoundingBox(px - 0.01, py - 0.01, pz - 0.01, px + 0.99, py + 0.99, pz + 0.99);
				        
				        switch(color){
				        	case 0: GL11.glColor4d(0, 0.56, 1, 0.25); break;
				        	case 1: GL11.glColor4d(0.11, 0.91, 0.0D, 0.25); break;
				        	case 2: GL11.glColor4d(0.88, 0.83, 0.0D, 0.25); break;
				        	case 3: GL11.glColor4d(0.75, 0.0, 0.91, 0.25); break;
				        }
			        	drawCrossedOutlinedBoundingBox(bb);
			        	switch(color){
				        	case 0: GL11.glColor4d(0, 0.56, 1, 0.25); break;
				        	case 1: GL11.glColor4d(0.11, 0.91, 0.0D, 0.25); break;
				        	case 2: GL11.glColor4d(0.88, 0.83, 0.0D, 0.25); break;
				        	case 3: GL11.glColor4d(0.75, 0.0, 0.91, 0.25); break;
			        	}
			        	drawBoundingBoxQuads(bb);
			        	GL11.glPopMatrix();
	
				        GL11.glDepthMask(true);
				        GL11.glDisable(2848);
				        GL11.glDisable(3042);
				        GL11.glEnable(3553);
				        GL11.glEnable(2929);
				        GL11.glDisable(32925);
				        GL11.glEnable(2896);	        	
					}
				}
			}catch(NullPointerException e){System.out.println("An exception has been thrown!");}
		}
	}
	
	private void drawBoundingBoxQuads(AxisAlignedBB bb) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.draw();
		t.startDrawingQuads();
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.draw();
	}

	public static void drawCrossedOutlinedBoundingBox(AxisAlignedBB bb){
		Tessellator t = Tessellator.instance;
		t.startDrawing(GL11.GL_LINE_STRIP);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.draw();
		t.startDrawing(GL11.GL_LINE_STRIP);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.draw();
		t.startDrawing(GL11.GL_LINE_STRIP);
		t.addVertex(bb.maxX, bb.minY, bb.maxZ);
		t.addVertex(bb.maxX, bb.maxY, bb.maxZ);
		t.draw();
		t.startDrawing(GL11.GL_LINE_STRIP);
		t.addVertex(bb.maxX, bb.minY, bb.minZ);
		t.addVertex(bb.maxX, bb.maxY, bb.minZ);
		t.draw();
		t.startDrawing(GL11.GL_LINE_STRIP);
		t.addVertex(bb.minX, bb.minY, bb.maxZ);
		t.addVertex(bb.minX, bb.maxY, bb.maxZ);
		t.draw();
		t.startDrawing(GL11.GL_LINE_STRIP);
		t.addVertex(bb.minX, bb.minY, bb.minZ);
		t.addVertex(bb.minX, bb.maxY, bb.minZ);
		t.draw();
	}
	
	public int getColor(Double time){
		if (time < 0) return 3;
		if (time < 24) return 2;
		if (time < 168) return 1;
		return 0;
	}
}