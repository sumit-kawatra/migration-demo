import java.io.File;
import java.net.URL;
import java.security.ProtectionDomain;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public final class EmbeddedJettyServer {
	public static void main(String[] args) throws Exception {

		String portStr = args[0];
		String tmpDir = args[1];

		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		connector.setMaxIdleTime(1000 * 60 * 3);
		connector.setSoLingerTime(-1);
		connector.setPort(Integer.parseInt(portStr));
		server.setConnectors(new Connector[] {
			connector
		});

		WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath("/hawthorne-server");

		ProtectionDomain protectionDomain = EmbeddedJettyServer.class.getProtectionDomain();
		URL location = protectionDomain.getCodeSource().getLocation();
		context.setTempDirectory(new File(tmpDir));
		context.setWar(location.toExternalForm());
		// context.setExtractWAR(false);

		server.setHandler(context);

		try {
			System.out.println("Starting embedded server...");
			server.start();
			// System.in.read();
			// server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
