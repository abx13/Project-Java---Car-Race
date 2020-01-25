package mvc;

import java.io.IOException;

public interface UpdateEventSender {
	public void add(UpdateEventListener listener);
	public void update() throws IOException;
}
