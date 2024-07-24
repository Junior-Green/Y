import { useUser } from "@/hooks/hooks";
import { CgHome } from "react-icons/cg";
import { IoSearchOutline } from "react-icons/io5";
import React, { useEffect, useMemo, useState } from "react";
import { BsPerson } from "react-icons/bs";
import { FiSettings } from "react-icons/fi";
import { FaRegBookmark } from "react-icons/fa6";
import { useLocation, useNavigate } from "react-router-dom";
import { HiOutlineDotsHorizontal } from "react-icons/hi";
import Logo from "./Logo";
import avatar from "@/assets/profile_picture.jpg"
import { IconBaseProps } from "react-icons";
import LogoutModal from "./LogoutModal";

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
    const navigate = useNavigate()
    const location = useLocation()
    const [logoutModalShowing, setLogoutModalShowing] = useState(false)


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

    return (
        <>
            <div className="w-min h-full flex-col flex py-4 px-3 md:ml-auto ml-0">
                <div className="w-min flex items-center transition-colors hover:bg-gray-500 hover:bg-opacity-20 rounded-full p-3 hover:cursor-pointer" onClick={() => navigate("/home")}>
                    <Logo widthValue={25} widthUnit={"px"} hexColor={"#ffffff"}></Logo>
                </div>

                <ul className="flex flex-col ">
                    {options.map(({ label, route, component }, index) => {
                        return (
                            <li key={index} style={{
                                color: location.pathname === route ? blueHexCode : "white"
                            }} className="w-min mr-auto flex items-center my-1 transition-colors hover:bg-gray-500 hover:bg-opacity-20 rounded-full p-3 hover:cursor-pointer" onClick={() => navigate(route)}>
                                {component}
                                <span className="text-xl ml-4 mr-3 md:block hidden">{label}</span>
                            </li>
                        )
                    })}
                </ul>
                <button className="bg-y-accent-blue rounded-full text-white font-semibold text-lg p-3 w-[225px] mt-2 transition-colors hover:bg-opacity-85">Post</button>
                <div className="w-[275px] mt-auto flex items-center transition-colors hover:bg-gray-500 hover:bg-opacity-20 rounded-full p-3 hover:cursor-pointer" onClick={expandProfileOptions}>
                    <img src={avatar} className="rounded-full" width={40}></img>
                    <div className="ml-3 flex flex-col w-full overflow-hidden overflow-ellipsis">
                        <span className="text-white font-bold leading-tight">{user?.displayName}</span>
                        <span className="text-y-gray-300">{`@${user?.username}`}</span>
                    </div>
                    <HiOutlineDotsHorizontal className="ml-auto" size={30} />
                </div>
            </div>
            {logoutModalShowing && <LogoutModal onClose={onLogoutModalClose} />}
        </>

    )
}

export default NavigationSideBar