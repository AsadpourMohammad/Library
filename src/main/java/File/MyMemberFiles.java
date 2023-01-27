package File;

import Person.Member;
import java.io.*;
import java.util.Arrays;

public class MyMemberFiles extends Files {
    protected static final String membersPath = path + "\\members.txt";

    public static void readMembers() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(membersPath));)
        {
            while (true) {
                Member member = (Member) in.readObject();
                members.add(member);
            }
        }
        catch (EOFException ignored) {}
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        finally {
            if(members.size() != 0) {
                Member.setStaticID(members.get(members.size() - 1).getID() + 1);
            }
        }
    }

    public static String writeMembers(boolean saveOrRemove) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(membersPath))){
            for(Member member : members){
                out.writeObject(member);
            }
            if (saveOrRemove)
                return "Member added to system.";
            else
                return "Member was removed from the system.";
        } catch (IOException ex) {
            return Arrays.toString(ex.getStackTrace());
        }
    }

    public static Member getMember(int memberID) {
        Member chosenMember = null;
        for (Member member : members){
            if(member.getID() == memberID){
                chosenMember = member;
                break;
            }
        }
        return chosenMember;
    }

    public static void showMembers() {
        if (members.size() == 0)
            System.out.println("No member was found.");
        else
            for(Member member : members)
                System.out.println(member);
    }
}
