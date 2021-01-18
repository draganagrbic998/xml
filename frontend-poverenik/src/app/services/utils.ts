export const dateToString = (date: Date): string => {
    if (!date){
      return '';
    }
    return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
};
