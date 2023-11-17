package K1_Practice;

import java.util.*;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException(String category) {
        super(String.format("Category %s was not found", category));
    }
}

class Category {
    String name;

    public Category(String name) {
        this.name = name;
    }

    public boolean equals(Category category) {
        return name.equals(category.name);
    }
}

abstract class NewsItem {
    String title;
    Date date;
    Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public long minutes() {
        return (new Date().getTime() - date.getTime()) / 1000 / 60;
    }

    abstract public String getTeaser();
}

class TextNewsItem extends NewsItem {
    String text;

    public TextNewsItem(String title, Date date, Category category, String text) {
        super(title, date, category);
        this.text = text;
    }

    @Override
    public String getTeaser() {
        return String.format("%s%n%s%n%s%n", title, minutes(), text.substring(0, text.length() > 80 ? 80 : text.length() - 1));
    }
}

class MediaNewsItem extends NewsItem {
    String url;
    int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        super(title, date, category);
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTeaser() {
        return String.format("%s%n%s%n%s%n%s%n", title, minutes(), url, views);
    }
}

class FrontPage {
    Category[] categories;
    List<NewsItem> newsList;

    public FrontPage(Category[] categories) {
        newsList = new ArrayList<>();
        this.categories = Arrays.copyOf(categories, categories.length);
    }

    public void addNewsItem(NewsItem newsItem) {
        newsList.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category) {
        return newsList.stream().filter(newsItem -> newsItem.category.equals(category)).collect(Collectors.toList());
    }

    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        Category categoryInput = new Category(category);

        if (Arrays.stream(categories).noneMatch(category1 -> category1.equals(categoryInput))) throw new CategoryNotFoundException(category);
        return newsList.stream().filter(newsItem -> newsItem.category.equals(categoryInput)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        newsList.forEach(newsItem -> sb.append(newsItem.getTeaser()));
        return sb.toString();
    }
}