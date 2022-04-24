package concurrent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PageIterator {
    private PageReader pageReader;
    private URLIterator urls;

    public synchronized String getNExtPageOrNull() {
        if (urls.hasNext()) {
            return getPageFor(urls.next());
        } else {
            return null;
        }
    }

    public String getPageFor(String url) {
        return reader.getPageFor(url);
    }
}
