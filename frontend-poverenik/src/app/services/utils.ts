export const dateToString = (date: Date): string => {
  if (!date){
    return '';
  }
  return `${date.getDate()}.${date.getMonth() + 1}.${date.getFullYear()}.`;
};

export const saveMetadata = (text: string, format: string) => {
const a: any = document.createElement('a');
a.style = 'display: none';
document.body.appendChild(a);

const url: string = window.URL.createObjectURL(new Blob([text], {type: format === 'rdf' ? 'text/xml' : 'application/json'}));
a.href = url;
a.download = 'download' + (format === 'rdf' ? '.xml' : '.json');
a.click();
window.open(url, '_blank');
};


