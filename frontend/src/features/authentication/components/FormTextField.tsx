import { OutlinedInputProps, TextField, TextFieldProps, alpha, inputLabelClasses, styled } from "@mui/material";


const FormInputField = styled((props: TextFieldProps) => (
    <TextField
        InputProps={{ disableUnderline: true } as Partial<OutlinedInputProps>}
        InputLabelProps={{
            sx: {
                color: "#AAB8C2",
                [`&.${inputLabelClasses.shrink}`]: {
                    color: "#1DA1F2"
                }
            }
        }}
        {...props}
    />
))(({ theme }) => ({
    '& .MuiFilledInput-root': {
        overflow: 'hidden',
        borderRadius: 4,
        backgroundColor: 'transparent',
        border: '1px solid',
        borderColor: "#333639",
        transition: theme.transitions.create([
            'border-color',
            'background-color',
            'box-shadow',
        ]),
        '&:hover': {
            backgroundColor: 'transparent',
        },
        '&.Mui-focused': {
            backgroundColor: 'transparent',
            boxShadow: `${alpha(theme.palette.primary.main, 0.25)} 0 0 0 2px`,
            borderColor: theme.palette.primary.main,
        },
    },
}));

export default FormInputField