package K1_Practice;

import java.time.LocalDate;
import java.util.*;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();

            LocalDate dateToOpen = date.atStartOfDay().plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(int id) {
        super(String.format("Item with id %d doesn't exist", id));
    }
}

abstract class Archive {
    int id;
    LocalDate dateArchived;

    public Archive(int id) {
        this.id = id;
    }

    public void archive(LocalDate date) {
        dateArchived = date;
    }

    public abstract void open(LocalDate date, List<String> logs);
}

class LockedArchive extends Archive {
    LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    @Override
    public void open(LocalDate date, List<String> logs) {
        if (dateToOpen.isAfter(date)) logs.add(String.format("Item %d cannot be opened before %s%n", id, dateToOpen));
        else logs.add(String.format("Item %d opened at %s%n", id, date));
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int timesOpened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        timesOpened = 0;
    }

    @Override
    public void open(LocalDate date, List<String> logs) {
        if (timesOpened++ >= maxOpen) logs.add(String.format("Item %d cannot be opened more than %d times%n", id, maxOpen));
        else logs.add(String.format("Item %d opened at %s%n", id, date));
    }
}

class ArchiveStore {
    List<Archive> archives;
    List<String> logs;

    public ArchiveStore() {
        archives = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public void archiveItem(Archive item, LocalDate date) {
        item.archive(date);

        archives.add(item);
        logs.add(String.format("Item %d archived at %s%n", item.id, date));
    }

    public void openItem(int id, LocalDate date) throws NonExistingItemException {
        if (archives.stream().noneMatch(archive -> archive.id == id)) throw new NonExistingItemException(id);

        archives.stream().filter(archive -> archive.id == id).findFirst().get().open(date, logs);
    }

    public String getLog() {
        StringBuilder sb = new StringBuilder();

        logs.forEach(sb::append);
        return sb.toString();
    }
}