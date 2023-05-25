import classes from './Input.module.css'

const Input = (props: any) => {
    return (
        <>
        <label htmlFor={props.id}>{props.placeholder}</label><br/>
        <input
          placeholder={props.placeholder}
          type={props.type}
          className={`${classes.input} ${props.isValid === false && classes.invalid} ${props.className}`}
          id={props.id}
          onChange={props.onChange}
          value={props.value}
        ></input>
        {(props.isValid === false && props.showError) && <span className={classes.invalid__message}>{props.id} invalid</span>}
        </>
    )
}

export default Input