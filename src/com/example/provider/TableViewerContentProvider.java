package com.example.provider;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TableViewerContentProvider implements IStructuredContentProvider{

	   @Override  
       public void dispose() {  
           // TODO Auto-generated method stub  
             
       }  
 
       @Override  
       public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {  
       	
       }  
 
       @Override  
       public Object[] getElements(Object inputElement) {  
           // TODO Auto-generated method stub  
           if(inputElement instanceof List){  
               return ((List) inputElement).toArray();  
           }  
           return new Object[0];  
       }  

}
