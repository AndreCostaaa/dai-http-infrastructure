import { Input } from "@chakra-ui/react";
import React from "react";

interface Props {
  onFileUpload: (files: FileList | null) => void;
}
const FileUploadInput = ({ onFileUpload }: Props) => {
  const handleFileUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;
    onFileUpload(e.target.files);
  };
  return (
    <>
      <Input
        type="file"
        multiple
        accept="image/*"
        onChange={handleFileUpload}
      />
    </>
  );
};

export default FileUploadInput;
