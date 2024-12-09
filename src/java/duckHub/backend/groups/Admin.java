package duckHub.backend.groups;

public interface Admin extends Member {
    void approveRequest();
    void rejectRequest();
    void deletePost();
    void editPost();
    void removeMember();
}
