import { useUser } from "@/hooks/hooks";
import { AiFillHome } from "react-icons/ai";
import { IoSearchOutline } from "react-icons/io5";
import React from "react";
import { useParams } from "react-router-dom";

type SideBarOption = {
    label: string,
    route: string,
    component: React.ReactNode
}

const options: SideBarOption[] = [
    {
        label: "Home",
        component: <AiFillHome />,
        route: "/home"
    },
    {
        label: "Explore",
        route: "/explore",
        component: <IoSearchOutline />
    }
]

const NavigationSideBar = () => {
    const { user } = useUser();
    const {rou} = useParams();


    return (
        <div className="w-full h-full flex-col flex ">

        </div>
    )
}

export default NavigationSideBar