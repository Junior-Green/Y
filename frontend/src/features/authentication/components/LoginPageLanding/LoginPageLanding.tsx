import Logo from "@/components/Logo"

interface LoginPageLandingProps {
    onSignIn: () => void;
    onCreateAccount: () => void;
}

const LoginPageLanding = ({onSignIn, onCreateAccount}: LoginPageLandingProps) => {

    return (
        <div className="w-full h-full flex flex-col items-start md:items-center md:justify-center md:flex-row p-5">
            <div className="w-10 md:w-1/2 md:mx-auto  mb-8 block md:flex md:justify-center md:h-1/2 ">
                <Logo widthValue={100} widthUnit="%" hexColor={"#FFFFFF"}></Logo>
            </div>
            <div className="w-full md:w-1/2 flex flex-col items-start md:justify-center text-white">
                <h1 className="text-4xl sm:text-7xl font-bold mb-10 sm:mb-16 leading-tight">Happening now</h1>
                <h2 className="text-2xl sm:text-4xl font-bold">Join today.</h2>
                <div className="w-full md:w-1/2 mt-7">
                    <button className="w-full bg-y-accent-blue rounded-full font-semibold py-2 px-2 min-w-60 max-w-80" onClick={onCreateAccount}>Create account</button>
                    <div className="flex items-center justify-center my-1 min-w-60 max-w-80">
                        <div className="border-t-[1px] border-t-y-gray-400 w-full"></div>
                        <span className="mx-2 text-y-gray-300 text-center pb-1 ">or</span>
                        <div className="border-t-[1px] border-t-y-gray-400 w-full"></div>
                    </div>
                    <button className="w-full border text-y-accent-blue rounded-full font-semibold py-2 px-2 transition-colors hover:bg-sky-950 hover:bg-opacity-40 min-w-60 max-w-80" onClick={onSignIn}>Sign In</button>
                </div>
            </div>
        </div>

    )
}

export default LoginPageLanding