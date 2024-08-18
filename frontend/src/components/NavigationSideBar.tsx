import { useUser } from "@/hooks/hooks";
import { CgHome } from "react-icons/cg";
import { IoSearchOutline } from "react-icons/io5";
import React, { useMemo, useState } from "react";
import { BsPerson } from "react-icons/bs";
import { FiSettings } from "react-icons/fi";
import { TbPencilPlus } from "react-icons/tb";
import { FaRegBookmark } from "react-icons/fa6";
import { Link, useLocation } from "react-router-dom";
import { HiOutlineDotsHorizontal } from "react-icons/hi";
import Logo from "./Logo";
import { IconBaseProps } from "react-icons";
import LogoutModal from "./LogoutModal";
import PostModal from "./PostModal";
import ProfileAvatar from "./ProfileAvatar";

type SideBarOption = {
    label: string,
    route: string,
    component: React.ReactNode
}

const iconProps: IconBaseProps = {
    color: "white",
    size: "30",
}

const blueHexCode = "#1DA1F2"

const NavigationSideBar = () => {
    const { user } = useUser();
    const location = useLocation()
    const [logoutModalShowing, setLogoutModalShowing] = useState(false)
    const [postModalShowing, setPostModalShowing] = useState(false)


    const options: SideBarOption[] = useMemo(() => [
        {
            label: "Home",
            component: <CgHome {...iconProps} color={location.pathname === "/home" ? blueHexCode : "white"} />,
            route: "/home"
        },
        {
            label: "Explore",
            route: "/explore",
            component: <IoSearchOutline {...iconProps} color={location.pathname === "/explore" ? blueHexCode : "white"} />
        },
        {
            label: "Bookmarks",
            route: "/bookmarks",
            component: <FaRegBookmark {...iconProps} color={location.pathname === "/bookmarks" ? blueHexCode : "white"} />
        },
        {
            label: "Profile",
            route: `/${user?.username}`,
            component: <BsPerson {...iconProps} color={location.pathname === `/${user?.username}` ? blueHexCode : "white"} />
        },
        {
            label: "Settings",
            route: "/settings",
            component: <FiSettings {...iconProps} color={location.pathname === "/settings" ? blueHexCode : "white"} />
        }
    ]
        , [user, location])

    const expandProfileOptions = () => {
        setLogoutModalShowing(true)
    }

    const onLogoutModalClose = () => {
        setLogoutModalShowing(false); //
    }

    const onPostModalClose = () => {
        setPostModalShowing(false); //
    }

    return (
        <>
            <div className="w-min h-full  flex-col flex py-4 px-2 md:px-3 md:ml-auto ml-0">
                <Link className="w-min flex items-center transition-colors hover:bg-gray-500 hover:bg-opacity-20 rounded-full p-3 hover:cursor-pointer" to={"/home"}>
                    <Logo widthValue={25} widthUnit={"px"} hexColor={"#ffffff"}></Logo>
                </Link>

                <ul className="flex flex-col ">
                    {options.map(({ label, route, component }, index) => {
                        return (
                            <li
                                key={index}
                                style={{ color: location.pathname === route ? blueHexCode : "white" }}>
                                <Link className="w-min mr-auto flex items-center my-1 transition-colors hover:bg-gray-500 hover:bg-opacity-20 rounded-full p-3 hover:cursor-pointer" to={route}>
                                    {component}
                                    <span className="text-xl ml-4 mr-3 md:block hidden">{label}</span>
                                </Link>
                            </li>
                        )
                    })}
                </ul>
                <button className="bg-y-accent-blue w-min rounded-full p-4 md:p-3 md:w-[225px] mt-2 transition-colors hover:bg-opacity-85 self-center md:self-start" onClick={() => setPostModalShowing(true)}>
                    <span className="hidden md:block text-white font-semibold text-lg ">Post</span>
                    <TbPencilPlus className="block md:hidden" size={25} />
                </button>
                <div className="w-auto md:w-[250px] mt-auto flex justify-center items-center transition-colors hover:bg-gray-500 hover:bg-opacity-20 rounded-full p-3 hover:cursor-pointer" onClick={expandProfileOptions}>
                    <ProfileAvatar name={user?.displayName ?? "Y"} width={40} height={40} />
                    <div className="ml-3 hidden flex-col w-full overflow-hidden overflow-ellipsis md:flex">
                        <span className="text-white font-bold leading-tight">{user?.displayName}</span>
                        <span className="text-y-gray-300">{`@${user?.username}`}</span>
                    </div>
                    <HiOutlineDotsHorizontal className="ml-auto hidden md:block" size={30} />
                </div>
            </div>
            {logoutModalShowing && <LogoutModal onClose={onLogoutModalClose} />}
            {postModalShowing && <PostModal onClose={onPostModalClose} />}
        </>

    )
}

export default NavigationSideBar