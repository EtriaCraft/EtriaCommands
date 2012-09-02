package Main;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerData {

	private FileConfiguration pdata = null;
	private File pdata2 = null;
	
	public PlayerData(OfflinePlayer p) {
		File dataFolder = EtriaCommands.dataFolder;
		pdata2 = new File(dataFolder + File.separator + "playerdata" + File.separator + p.getName().toLowerCase() + ".yml");
		if (!pdata2.exists()) return;
		pdata = YamlConfiguration.loadConfiguration(pdata2);
	}
	
	public PlayerData(String p) {
		File dataFolder = EtriaCommands.dataFolder;
		pdata2 = new File(dataFolder + File.separator + "playerdata" + File.separator + p.toLowerCase() + ".yml");
		if (!pdata2.exists()) return;
		pdata = YamlConfiguration.loadConfiguration(pdata2);
	}
	
	public boolean createFile() {
		if (pdata != null) return false;
		try {
			pdata2.getParentFile().mkdirs();
			boolean success = pdata2.createNewFile();
			pdata = YamlConfiguration.loadConfiguration(pdata2);
			return success;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void setString(String value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setLong(long value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDouble(double value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void set(Object value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setInteger(Integer value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setStringList(List<String> value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setConfigurationSection(ConfigurationSection value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setBoolean(Boolean value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setFloat(Float value, String path) {
		if (pdata == null) return;
		pdata.set(path, value);
		try {
			pdata.save(pdata2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getStringList(String path) {
		if (pdata == null) return null;
		return pdata.getStringList(path);
	}
	
	public boolean getBoolean(String path) {
		return pdata != null && pdata.getBoolean(path);
	}
	
	public Integer getInteger(String path) {
		if (pdata == null) return null;
		return pdata.getInt(path);
	}
	
	public Object get(String path) {
		if (pdata == null) return null;
		return pdata.get(path);
	}
	
	public Long getLong(String path) {
		if (pdata == null) return null;
		return pdata.getLong(path);
	}
	
	public Double getDouble(String path) {
		if (pdata == null) return null;
		return pdata.getDouble(path);
	}
	
	public String getString(String path) {
		if (pdata == null) return null;
		return pdata.getString(path);
	}
	
	public Float getFloat(String path) {
		if (pdata == null) return null;
		try {
			return Float.valueOf(pdata.getString(path));
		} catch (Exception e) {
			return null;
		}
	}
	
	public ConfigurationSection getConfigurationSection(String path) {
		if (pdata == null) return null;
		return pdata.getConfigurationSection(path);
	}
	
	public boolean getConfExists() {
		return pdata != null;
	}
	
	public boolean exists() {
		return pdata != null;
	}

}