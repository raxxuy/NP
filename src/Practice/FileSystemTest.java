package Practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSystemTest {

    public static Folder readFolder (Scanner sc)  {

        Folder folder = new Folder(sc.nextLine());
        int totalFiles = Integer.parseInt(sc.nextLine());

        for (int i=0;i<totalFiles;i++) {
            String line = sc.nextLine();

            if (line.startsWith("0")) {
                String fileInfo = sc.nextLine();
                String [] parts = fileInfo.split("\\s+");
                try {
                    folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                try {
                    folder.addFile(readFolder(sc));
                } catch (FileNameExistsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return folder;
    }

    public static void main(String[] args)  {

        //file reading from input

        Scanner sc = new Scanner (System.in);

        System.out.println("===READING FILES FROM INPUT===");
        FileSystem fileSystem = new FileSystem();
        try {
            fileSystem.addFile(readFolder(sc));
        } catch (FileNameExistsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("===PRINTING FILE SYSTEM INFO===");
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
        fileSystem.sortBySize();
        System.out.println(fileSystem.toString());

        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
        System.out.println(fileSystem.findLargestFile());




    }
}

class FileNameExistsException extends Exception {
    public FileNameExistsException(String fileName, String folderName) {
        super(String.format("There is already a file named %s in the folder %s", fileName, folderName));
    }
}

interface IFile {
    String getFileName();
    long getFileSize();
    String getFileInfo(int indent);
    void sortBySize();
    File findLargestFile();
}

class File implements IFile {
    private String name;
    private long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        return String.format("%sFile name: %10s File size: %10d\n", IndentPrinter.printIndent(indent), name, size);
    }

    @Override
    public void sortBySize() {}

    @Override
    public File findLargestFile() {
        return this;
    }
}

class Folder implements IFile {
    private String name;
    private long size;
    private List<IFile> iFiles;

    public Folder(String name) {
        this.name = name;
        size = 0;
        iFiles = new ArrayList<>();
    }

    public void addFile(IFile file) throws FileNameExistsException {
        if (iFiles.stream().anyMatch(iFile -> iFile.getFileName().equals(file.getFileName()))) throw new FileNameExistsException(file.getFileName(), name);

        iFiles.add(file);
        size += file.getFileSize();
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public long getFileSize() {
        return size;
    }

    @Override
    public String getFileInfo(int indent) {
        StringBuilder sb = new StringBuilder(String.format("%sFolder name: %10s Folder size: %10d\n", IndentPrinter.printIndent(indent), name, size));

        iFiles.forEach(file -> sb.append(file.getFileInfo(indent + 1)));
        return sb.toString();
    }

    @Override
    public void sortBySize() {
        iFiles.sort(Comparator.comparing(IFile::getFileSize));
        iFiles.forEach(IFile::sortBySize);
    }

    @Override
    public File findLargestFile() {
        return iFiles.stream().max(Comparator.comparing(IFile::getFileSize)).get().findLargestFile();
    }
}

class FileSystem {
    private Folder rootDirectory;

    public FileSystem() {
        rootDirectory = new Folder("root");
    }

    public void addFile(IFile file) throws FileNameExistsException {
        rootDirectory.addFile(file);
    }

    public long findLargestFile() {
        return rootDirectory.findLargestFile().getFileSize();
    }

    public void sortBySize() {
        rootDirectory.sortBySize();
    }

    @Override
    public String toString() {
        return rootDirectory.getFileInfo(0);
    }
}

class IndentPrinter {
    public static String printIndent(int level) {
        return IntStream.range(0, level).mapToObj(i -> "\t").collect(Collectors.joining());
    }
}