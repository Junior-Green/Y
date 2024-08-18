import { CircularProgressProps, Box, CircularProgress, circularProgressClasses } from "@mui/material";


const LoadingIndicator = (props: CircularProgressProps) => {
    return (
        <Box sx={{ position: 'relative', height: props.size }}>
            <CircularProgress
                variant="determinate"
                sx={{
                    color: (theme) =>
                        theme.palette.grey[theme.palette.mode === 'light' ? 200 : 800],
                    margin: "auto"
                }}
                size={40}
                thickness={4}
                {...props}
                value={100}
            />
            <CircularProgress
                variant="indeterminate"
                disableShrink
                sx={{
                    animationDuration: '550ms',
                    margin: "auto",
                    position: 'absolute',
                    left: 0,
                    [`& .${circularProgressClasses.circle}`]: {
                        strokeLinecap: 'round',
                    },
                }}
                size={40}
                thickness={4}
                {...props}
            />
        </Box>
    );
}

export default LoadingIndicator