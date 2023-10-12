export interface IInputProps {
  id: string;
  placeholder: string;
  type: string;
  className?: string;
  name: string
  isValid: boolean | null;
  showError?: boolean;
  value?: string | number;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}