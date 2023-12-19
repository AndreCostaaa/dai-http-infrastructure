package dai.database;

public record Role(int id,
                   String name,
                   boolean canCreate,
                   boolean canAssignOthers,
                   boolean isMechanic) {
    //Role requests @TODO

    /**
     * fetch Role matching given id from database
     * @return Person or null
     */
    static public Role fetchOne(int id){
        return null;
    }

    /**
     * fetch all Roles from database
     * @return Role[] or null
     */
    static public Role[] fetchAll(){
        return null;
    }

    /**
     * create a new role in the database
     * @return true if successful
     */
    static public boolean create(Role role){
        return true;
    }

    /**
     * delete role from database matching id
     * @return true if successful
     */
    static public boolean delete(int roleId){
        return true;
    }

    /**
     * update role in database matching id of given role
     * @return true if successful
     */
    static public boolean update(Role role){
        return true;
    }
}
