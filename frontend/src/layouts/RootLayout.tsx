import ExploreSideBar from "@/components/ExploreSideBar";
import NavigationSideBar from "@/components/NavigationSideBar";
import { Outlet } from "react-router-dom";

function RootLayout() {

    return (
        <div className="w-full h-auto flex relative">
            <section className="w-auto h-screen md:w-1/3 sticky top-0">
                <NavigationSideBar />
            </section>
            <section className="w-full h-auto md:max-w-[600px] border-x-[1px] border-x-y-gray-400" >
                <Outlet></Outlet>
            </section>
            <section className=" h-screen sticky top-0">
                <ExploreSideBar />
            </section>
        </div>)
}


export default RootLayout;