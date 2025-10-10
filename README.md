Hi this is 2473's guide on how to use Simulation

First if you're not using a laptop with WPI already installed you need to download it.

## How to install WPILib
1. Go to this [link](https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/wpilib-setup.html)
2. Scroll down and install the WPI Installer
    - Once you have opened the installer you will see a welcome message, click Start in the bottom right
    - For the install mode select everything and then install for this user
    - Then you should select the option "Download for this computer only(fastest)"
> [!NOTE]
> Its about 2 GB so it might take a while.

## Using WPILib for Simulation

First you need code to actually execute

1. Git Set Up
    - Git is how SW does version control or in other words how we log our code so we can go back to previous versions, you will learn a lot more if you are on SW
    - If you already have git installed you can skip this step.
    - If you are on a Mac, go to your terminal and type git version, the terminal will prompt you to download the needed tools and you should accept
    - If you're on Windows you will need to the link below and follow the instructions to download git [link](https://git-scm.com/downloads/win)
2. Cloning the Repo
    - Go to this [link](https://github.com/vismaybhargav/WPILibSimActivity)
    - Click the bright green button labled code, copy the link you see
    - Once you have copied the link, go to your terminal and navigate to your Projects folder, if you dont have one make one once you are in projects folder, type "git clone" followed by the link
    - This will create a folder in your Projects folder called WPILibSimActivity this is the code you're executing
    - If you are trying to simulate a different repository, its the same steps just go to THAT repository link.
    - *Note: Sim has to be set up on a repo in code before running*
3. How to run Simulation
    - Open WPILib and then open the WPILibSimActivity repository in WPI
    - Now click the WPI logo in the top right and type Simulate Robot code
    - A prompt will come up at the top of your screen, select Sim Gui and click Ok
    - Once Simulation opens, go back to WPILib and click the logo again
    - Select Start tool and choose advantage scope.
    - Once you do that, in the top right click File -> Connect to Simulator
    - Then a bunch of tabs should come up on the left side
    - On the top right click + and choose 3D Field then go to that tab
    - On the left side go to AdvantageKit-> Swerve and then drag DriveTrain Pose to the bottom of the Page labeled Poses
    - Now go back to The simulation window, at the top of you screen go to DS, make sure 
