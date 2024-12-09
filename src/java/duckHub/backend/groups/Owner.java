package duckHub.backend.groups;

public interface Owner extends Admin {
    void promoteMember();
    void demoteAdmin();
    void deleteGroup();
}
