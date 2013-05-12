package nl.thijsmolendijk.Client.Modules;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {
    //All loaded modules
	public List<IModule> loadedModules = new ArrayList<IModule>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        new Main(new File("modules")); //Modules folder
	}

	public Main(File modFolder) {
        
		System.out.println("Loading modules at: "+modFolder.getAbsolutePath());
        
		for (File potentialMod : modFolder.listFiles()) {
            
			System.out.println("Found potential mod: "+potentialMod.getAbsolutePath());
            
			if (potentialMod.getName().endsWith(".jar"))
                try {
                    this.loadModFiles(potentialMod.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
		}
        
        //Shows that the modules actualy are loaded and work
		for (IModule m : loadedModules) {
			System.out.println(m.getState());
		}
	}

	@SuppressWarnings("rawtypes")
	public void loadModFiles(String location) throws Exception {
		JarFile jarFile = new JarFile(location);
		Enumeration<JarEntry> e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + location+"!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (e.hasMoreElements()) {
			JarEntry je = (JarEntry) e.nextElement();
			if(je.isDirectory() || !je.getName().endsWith(".class")){
				continue;
			}
			// -6 because of .class
			String className = je.getName().substring(0,je.getName().length()-6);
			className = className.replace('/', '.');
			Class c = cl.loadClass(className);
			if (hasAnnotation(c)) {
                //Try to get the version / name
				String name = "";
				String version = "";
				for(Annotation annotation : c.getAnnotations()){
					if(annotation instanceof UndercastModule){
						UndercastModule undercastAnnotation = (UndercastModule) annotation;
						name = undercastAnnotation.name();
						version = undercastAnnotation.version();
					}
				}
                Object obj = c.newInstance();
                if (obj instanceof IModule) {
                    System.out.println("Loaded module " + name + " v"+version);
                    this.loadedModules.add((IModule) c.newInstance());
                } else {
                    System.out.println("Nag the author of "+name+" about the fact he doesn't implement IModule!");
                }
			}
		}
	}

    //Check if the class has @UndercastModule
	@SuppressWarnings("rawtypes")
	private boolean hasAnnotation(Class obj) {
		boolean found = false;
		for(Annotation annotation : obj.getAnnotations()) {
			if (annotation instanceof UndercastModule)
				found = true;
		}
		return found;
	}
}
