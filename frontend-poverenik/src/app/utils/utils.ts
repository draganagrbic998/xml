export const formatDate = (date: Date): string =>
{
    date = new Date();
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
};
