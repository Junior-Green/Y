import { stringToColor } from "@/utils/helpers";
import Avatar from "@mui/material/Avatar";

interface ProfileAvatarProps {
    name: string;
    width: number;
    height: number;
}

function generateAvatarProps(name: string, width: number, height: number){
    return {
        sx: {
            bgcolor: stringToColor(name),
            width: width,
            height: height
        },
        children: `${name.split(' ')[0][0].toUpperCase()}`,
    };
}

const ProfileAvatar = ({ name, width, height}: ProfileAvatarProps) => <Avatar {...generateAvatarProps(name, width, height)} />


export default ProfileAvatar
