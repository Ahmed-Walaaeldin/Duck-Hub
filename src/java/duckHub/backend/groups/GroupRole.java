
package duckHub.backend.groups;

public class GroupRole {
    private final String groupName;
    private final String groupId;
    private Role role;
    private final String userId;

    public GroupRole(String groupName, String groupId, Role role, String userId) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.role = role;
        this.userId = userId;
    }

    // set Role
    public void setRole(Role role) {
        this.role = role;
    }

    // Getters
    public String getGroupName() {
        return groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public Role getRole() {
        return this.role;
    }

    public String getUserId() {
        return userId;
    }
}
