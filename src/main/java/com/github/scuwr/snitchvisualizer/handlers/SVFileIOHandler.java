package com.github.scuwr.snitchvisualizer.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.github.scuwr.snitchvisualizer.SV;
import com.github.scuwr.snitchvisualizer.SVSettings;
import com.github.scuwr.snitchvisualizer.classobjects.Snitch;

import net.minecraft.client.Minecraft;

/**
 * File I/O Handler for Snitch Visualizer
 * 
 * @author Scuwr
 *
 */
public class SVFileIOHandler {

	public static File oldSnitchList = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/SnitchList.txt");
	public static File snitchList = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/mods/Snitch-Visualizer-" + SV.MODVERSION + "/SnitchList.csv");
	public static File svSettings = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/mods/Snitch-Visualizer-" + SV.MODVERSION + "/SVSettings.txt");
	public static File svDir = new File(Minecraft.getMinecraft().mcDataDir.toString() + "/mods/Snitch-Visualizer-" + SV.MODVERSION);
	public static boolean isDone = false;
	
	public static void saveList(){
		isDone = false;
		try {
			if(!svDir.exists()){
				SV.instance.logger.info("Creating Snitch Visualizer Directory");
				if(!svDir.mkdirs()){
					SV.instance.logger.error("Failed to create Snitch Visualizer Directory!");
				}
			}			
			if(!snitchList.exists()){				
				SV.instance.logger.info("Creating new file: SnitchList.csv");
				snitchList.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(snitchList));
			//bw.write("Config:\r\n");
			//bw.write("\tRendering: " + SV.instance.config.rendering + "\r\n");
			//bw.write("\tUpdateDetection: " + SV.instance.config.updateDetection + "\r\n");
			//bw.write("SnitchList:\r\n");
			for(Snitch n : SV.instance.snitchList){
				bw.write(n.x + "," + n.y + "," + n.z + "," + n.cullTime.getTime() + "," + n.ctGroup + "," + n.type + "," + "\r\n");
			}
			bw.close();
		} catch (IOException e){
			SV.instance.logger.error("Failed to write to SnitchList.csv!");
		}
		isDone = true;
	}
	
	public static void saveSettings(){
		isDone = false;
		try{
			if(!svDir.exists()){
				SV.instance.logger.info("Creating Snitch Visualizer Directory");
				if(!svDir.mkdirs()){
					SV.instance.logger.error("Failed to create Snitch Visualizer Directory!");
				}
			}			
			if(!svSettings.exists()){				
				SV.instance.logger.info("Creating new file: SVSettings.txt");
				svSettings.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(svSettings));
			bw.write(SV.settings.getKeyBinding(SVSettings.Options.UPDATE_DETECTION) + ";\r\n");
			bw.write(SV.settings.getKeyBinding(SVSettings.Options.RENDER_DISTANCE) + ";\r\n");
			bw.write(SV.settings.getKeyBinding(SVSettings.Options.RENDER_ENABLED) + ";\r\n");
			bw.write(SV.settings.getKeyBinding(SVSettings.Options.SETTINGS_KEYBINDING) + ";\r\n");

			bw.close();
		} catch (IOException e){
			SV.instance.logger.error("Failed to write to SVSettings.txt!");
		}
		isDone = true;
	}
	
	public static void loadList(){
		isDone = false;
		try{			
			if(!snitchList.exists()) saveList();
			
			BufferedReader br = null;
			
			if(oldSnitchList.exists()){
				br = new BufferedReader(new FileReader(oldSnitchList));
			}
			else{
				br = new BufferedReader(new FileReader(snitchList));
			}
			String line = br.readLine();
			while(line != null){
				String tokens[] = line.split(",|;");
				if(tokens.length > 5){
					SV.instance.snitchList.add(new Snitch(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), hoursToDate(Long.parseLong(tokens[3])), tokens[4], tokens[5]));
				}
				line = br.readLine();
			}
			br.close();
			
			if(oldSnitchList.exists()){
				oldSnitchList.delete();
				saveList();
			}
		} catch (IOException e){
			SV.instance.logger.error("Failed to load SnitchList.csv!");
		} catch (NullPointerException e){
			SV.instance.logger.error("SnitchList.csv does not exist!");
		}
		isDone = true;
	}

	private static double hoursToDate(long l) {
		Date oldDate = new Date();
	    return (l - oldDate.getTime()) / Snitch.HOURS_IN_MILLIS;
	}
}
