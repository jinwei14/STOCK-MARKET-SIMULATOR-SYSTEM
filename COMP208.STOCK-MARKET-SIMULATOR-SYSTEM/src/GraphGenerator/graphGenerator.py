import numpy as np
import matplotlib.pyplot as plt
import matplotlib.ticker as mticker
import matplotlib.dates as mdates
from matplotlib.finance import candlestick_ohlc
import sys

eachStock = 'Facebook','Microsoft' #stock names

def movingaverage(values,window): #the function to calculate moving average
    weights = np.repeat(1.0,window)/window
    smas = np.convolve(values, weights, 'valid')
    return smas
   
   
def dailyGraph(stock):
    try:
        stockFile=stock + 'Daily.txt' #file name
        
        print stockFile

        #read the attributes        
        date, price= np.loadtxt(stockFile,delimiter='\t',skiprows=1,usecols=(1,3),converters={1:mdates.strpdate2num('%Y-%m-%d %H:%M:%S')},unpack=True)
        #create a figure
        fig = plt.figure(facecolor='#666666')
        #set the title of the figure
        plt.suptitle(stock+' Stock Daily Price',color='w')
        
        ax1=plt.subplot(1,1,1,axisbg='#2B2B2B')
        #draw the graph
        ax1.plot(date, price, color='#EEEE00')   
        
        #set the label of y axis
        plt.ylabel('Price',color='w')
        #set the grid
        ax1.grid(True,color='#CFCFCF',alpha=0.5)
        #set the color of the x and y axis
        ax1.tick_params(axis='x',colors='#CFCFCF')
        ax1.tick_params(axis='y',colors='#CFCFCF')
        #set the format of the date
        ax1.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d %H:%M'))
        #set the number of locators
        ax1.xaxis.set_major_locator(mticker.MaxNLocator(10)) #maximum of 10 dates on x axis
        ax1.yaxis.set_major_locator(mticker.MaxNLocator(5))
        
        plt.show()
        #set the size of the figure
        fig.set_size_inches(18.5, 10.5)
        #save the figure
        fig.savefig(stock+'Daily.png',facecolor=fig.get_facecolor())
        
        
    except Exception, e:
        print 'Error:',str(e) #print the exception   
   
   
def veriGraph(stock):
    try:
        stockFile=stock+'.txt' #file name

        #read the attributes        
        date, predict, actual= np.loadtxt(stockFile,delimiter='\t',skiprows=1,usecols=(1,2,3),converters={1:mdates.strpdate2num('%Y-%m-%d')},unpack=True)
        #create a figure
        fig = plt.figure(facecolor='#666666')
        #set the title of the figure
        plt.suptitle(stock+' Stock Prediction Verification',color='w')
        
        ax1=plt.subplot(1,1,1,axisbg='#2B2B2B')
        #draw the graph
        ax1.plot(date, predict, color='#EEEE00',label='Predict') 
        ax1.plot(date, actual, color='#1C86EE',label='Actual') 
        
        #set the legend
        plt.legend(loc=0,prop={'size':10},fancybox=True)
        
        #set the label of y axis
        plt.ylabel('Price',color='w')
        #set the grid
        ax1.grid(True,color='#CFCFCF',alpha=0.5)
        #set the color of the x and y axis
        ax1.tick_params(axis='x',colors='#CFCFCF')
        ax1.tick_params(axis='y',colors='#CFCFCF')
        #set the format of the date
        ax1.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))
        #set the number of locators
        ax1.xaxis.set_major_locator(mticker.MaxNLocator(10)) #maximum of 10 dates on x axis
        ax1.yaxis.set_major_locator(mticker.MaxNLocator(5))
       
        
        plt.show()
        #set the size of the figure
        fig.set_size_inches(18.5, 10.5)
        #save the figure
        fig.savefig(stock+'Veri.png',facecolor=fig.get_facecolor())
        
        
    except Exception, e:
        print 'Error:',str(e) #print the exception   
        
   
def predGraph(stock):
    try:
        stockFile=stock+'.txt' #file name

        #read the attributes        
        date, price= np.loadtxt(stockFile,delimiter='\t',skiprows=1,usecols=(1,2),converters={1:mdates.strpdate2num('%Y-%m-%d')},unpack=True)
        #create a figure
        fig = plt.figure(facecolor='#666666')
        #set the title of the figure
        plt.suptitle(stock+' Stock Prediction',color='w')
        
        ax1=plt.subplot(1,1,1,axisbg='#2B2B2B')
        #draw the graph
        ax1.plot(date, price, color='#EEEE00')   
        
        #set the label of y axis
        plt.ylabel('Price',color='w')
        #set the grid
        ax1.grid(True,color='#CFCFCF',alpha=0.5)
        #set the color of the x and y axis
        ax1.tick_params(axis='x',colors='#CFCFCF')
        ax1.tick_params(axis='y',colors='#CFCFCF')
        #set the format of the date
        ax1.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))
        #set the number of locators
        ax1.xaxis.set_major_locator(mticker.MaxNLocator(10)) #maximum of 10 dates on x axis
        ax1.yaxis.set_major_locator(mticker.MaxNLocator(5))
        
        plt.show()
        #set the size of the figure
        fig.set_size_inches(18.5, 10.5)
        #save the figure
        fig.savefig(stock+'Pred.png',facecolor=fig.get_facecolor())
        
        
    except Exception, e:
        print 'Error:',str(e) #print the exception
        
    

def histGraph(stock): #the function to show a graph, parameter: the name of the stock
    try:
        stockFile=stock+'.txt' #file name

        #read the attributes        
        date, high, low, open, close, volume = np.loadtxt(stockFile,delimiter='\t',skiprows=1,usecols=(1,2,3,4,5,6),converters={1:mdates.strpdate2num('%Y-%m-%d')},unpack=True)

        #append the data to an array        
        x = 0
        y = len(date)
        candleAr = []
        while x < y:
            appendLine = date[x],open[x],high[x],low[x],close[x],volume[x]
            candleAr.append(appendLine)
            x+=1
            
        #time periods of moving average
        MA1 = 10
        MA2 = 30
        MA3 = 60
        #calculating the moving averages
        Av1 = movingaverage(close,MA1)
        Av2 = movingaverage(close,MA2)
        Av3 = movingaverage(close,MA3)
        #starting point of the moving averages
        SP = len(date[MA3-1:])
        #labels of the moving averages
        label1 = 'MA ' + str(MA1)
        label2 = 'MA ' + str(MA2)
        label3 = 'MA ' + str(MA3)
            
        rect_vol=[0.1,0.1,0.8,0.26] #space of the volumn graph
        rect_main=[0.1,0.4,0.8,0.55] #space of the candlestick graph
        
        
        #create a figure
        fig = plt.figure(facecolor='#666666')
        #set the title of the figure
        plt.suptitle(stock+' Stock History',color='w')
        
        #set the first sub figure
        ax1=fig.add_axes(rect_main,axisbg='#2B2B2B') 
        #draw the moving average graph
        ax1.plot(date[-SP:],Av1[-SP:],label=label1,linewidth=1.5,color='#1C86EE') 
        ax1.plot(date[-SP:],Av2[-SP:],label=label2,linewidth=1.5,color='y')
        ax1.plot(date[-SP:],Av3[-SP:],label=label3,linewidth=1.5,color='#D15FEE')
        #draw the candlestick graph
        candlestick_ohlc(ax1,candleAr[-SP:],width=1,colorup='#008B00',colordown='#FF3030')
        #set the label of y axis
        plt.ylabel('Price',color='w')
        #set the grid
        ax1.grid(True,color='#CFCFCF',alpha=0.5)
        #set the color of the x and y axis
        ax1.tick_params(axis='x',colors='#CFCFCF')
        ax1.tick_params(axis='y',colors='#CFCFCF')
        #set the legend
        ax1.legend(loc=0,prop={'size':10},fancybox=True)

        #set the second subfigure
        ax2=fig.add_axes(rect_vol,sharex=ax1,axisbg='#2B2B2B')
        #draw the volumn graph
        ax2.fill_between(date[-SP:], volume[-SP:], color='#EEEE00',alpha=0.5)       
        #set the label of y axis
        plt.ylabel('Volumn',color='w')
        #set the grid
        ax2.grid(True,color='#CFCFCF',alpha=0.5)
        #set the color of the x and y axis
        ax2.tick_params(axis='x',colors='#CFCFCF')
        ax2.tick_params(axis='y',colors='#CFCFCF')
        #set the format of the date
        ax2.xaxis.set_major_formatter(mdates.DateFormatter('%Y-%m-%d'))
        #set the number of locators
        ax2.xaxis.set_major_locator(mticker.MaxNLocator(10)) #maximum of 10 dates on x axis
        ax2.yaxis.set_major_locator(mticker.MaxNLocator(5))

        #show the graph
        plt.show()
        #set the size of the figure
        fig.set_size_inches(18.5, 10.5)
        #save the figure
        fig.savefig(stock+'.png',facecolor=fig.get_facecolor())

    except Exception, e:
        print 'Error:',str(e) #print the exception




stock=str(sys.argv[1])
graph=str(sys.argv[2])

if graph == 'hist':
    histGraph(stock)
    
elif graph == 'pred':
    predGraph(stock)
    
elif graph == 'daily':
    dailyGraph(stock)
    
elif graph =='veri':
    veriGraph(stock)
    
    
    
#histGraph('Microsoft')