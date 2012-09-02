package Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.bukkit.command.CommandSender;

public class CommandManager {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Method> commands = new HashMap();
    
    public void registerCommands(Class<?> clazz) {
        for(Method mthd : clazz.getMethods()) {
            if (!mthd.isAnnotationPresent(CommandHandle.class)) continue;
            
            commands.put(mthd.getAnnotation(CommandHandle.class).name(), mthd);
        }
    }
    
    public boolean execute(String cmd, CommandSender s, String[] args) throws CommandHandleException {
        Method mthd = commands.get(cmd);
        if (mthd == null) return false;
        
        checkPermission(s, mthd);
        
        if (args.length < mthd.getAnnotation(CommandHandle.class).minargs()) {
            throw new CommandHandleException("NOT_ENOUGH_ARGS");
        }
        
        return callMethod(mthd, s, args);
    }
    
    private boolean callMethod(Method mthd, CommandSender s, String[] args) {
        try {
            return (boolean) mthd.invoke(null, s, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Utils.log.severe("Failed to execute command: " + mthd.getAnnotation(CommandHandle.class).name());
            e.printStackTrace();
            return false;
        }
    }
    
    private void checkPermission(CommandSender s, Method mthd) throws CommandHandleException {
        if (!hasPermission(s, mthd)) {
            throw new CommandHandleException("NO_PERMISSION");
        }
    }
    
    private boolean hasPermission(CommandSender s, Method mthd) {
        String[] perms = mthd.getAnnotation(CommandHandle.class).perms();
        if (perms[0].isEmpty()) return true;
        for (String perm : perms) {
            if (s.hasPermission(perm)) return true;
        }
        return false;
    }
    
}
